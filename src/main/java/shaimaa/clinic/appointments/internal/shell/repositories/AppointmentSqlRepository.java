package shaimaa.clinic.appointments.internal.shell.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import shaimaa.clinic.appointments.internal.core.contracts.AppointmentRepository;
import shaimaa.clinic.appointments.internal.core.contracts.AppointmentsPublisher;
import shaimaa.clinic.appointments.internal.core.model.Appointment;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class AppointmentSqlRepository implements AppointmentRepository {
    private static final Logger LOG = LoggerFactory.getLogger(AppointmentSqlRepository.class);

    @PersistenceContext(unitName = "appointments")
    private EntityManager entityManager;

    private final TransactionTemplate transactionTemplate;
    private final AppointmentsPublisher appointmentsPublisher;

    AppointmentSqlRepository(
            @Qualifier("appointmentsTransactionManager") PlatformTransactionManager transactionManager,
            AppointmentsPublisher appointmentsPublisher
    ) {
        this.transactionTemplate = new TransactionTemplate(transactionManager);
        this.appointmentsPublisher = appointmentsPublisher;
    }

    @Override
    public List<Appointment> getUpcomingAppointments() {
        String sql = "%s %s".formatted(
                "SELECT *",
                "FROM appointment"
        );

        List<AppointmentEntity> appointmentEntities = entityManager.createNativeQuery(sql, AppointmentEntity.class).getResultList();

        LOG.debug("Retrieved: {}", appointmentEntities);

        return appointmentEntities
                .stream()
                .map(AppointmentEntity::toDomain)
                .toList();
    }

    @Override
    public Optional<Appointment> getAppointment(UUID appointmentId) {
        AppointmentEntity appointmentEntity = this.entityManager.find(AppointmentEntity.class, appointmentId);

        return Optional.ofNullable(appointmentEntity).map(AppointmentEntity::toDomain);
    }

    @Override
    public void save(Appointment appointment) {
        LOG.debug(
                "Saving appointment: {}",
                appointment
        );
        AppointmentEntity appointmentEntity = AppointmentEntity.from(appointment);
        LOG.debug(
                "Saving appointment entity: {}",
                appointmentEntity
        );

        this.transactionTemplate.executeWithoutResult(status ->
                this.entityManager.merge(appointmentEntity)
        );

        // Pushed only after the transaction has committed, so other modules never react to a change that was rolled back.
        this.appointmentsPublisher.publish(appointment.pullEvents());
    }
}
