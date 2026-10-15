package shaimaa.clinic.appointments.internal.core.model;

import lombok.Getter;
import lombok.ToString;
import shaimaa.clinic.appointments.internal.core.events.AppointmentCreated;
import shaimaa.clinic.shared.domain.AggregateRoot;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@ToString
public class Appointment extends AggregateRoot {
    private final UUID id;

    private final UUID slotId;

    private final LocalDateTime slotStartsAt;

    private final float slotCost;

    private final Patient patient;

    private AppointmentStatus status;

    private Appointment(
            UUID id,
            UUID slotId,
            LocalDateTime slotStartsAt,
            float slotCost,
            Patient patient,
            AppointmentStatus status
    ) {
        this.id = id;
        this.slotId = slotId;
        this.slotStartsAt = slotStartsAt;
        this.slotCost = slotCost;
        this.patient = patient;
        this.status = status;
    }

    public static Appointment newAppointment(
            UUID slotId,
            LocalDateTime slotStartsAt,
            float slotCost,
            UUID patientId,
            String patientFirstName,
            String patientLastName
    ) {
        Appointment appointment = new Appointment(
                UUID.randomUUID(),
                slotId,
                slotStartsAt,
                slotCost,
                Patient.of(
                        patientId,
                        patientFirstName,
                        patientLastName
                ),
                AppointmentStatus.SCHEDULED
        );

        appointment.raiseEvent(new AppointmentCreated(
                appointment.id,
                slotId,
                slotStartsAt,
                slotCost,
                patientId,
                patientFirstName,
                patientLastName
        ));

        return appointment;
    }

    public static Appointment of(
            UUID id,
            UUID slotId,
            LocalDateTime slotStartsAt,
            float slotCost,
            Patient patient,
            AppointmentStatus status
    ) {
        return new Appointment(
                id,
                slotId,
                slotStartsAt,
                slotCost,
                patient,
                status
        );
    }

    public void complete() {
        this.status = AppointmentStatus.COMPLETED;
    }

    public void cancel() {
        this.status = AppointmentStatus.CANCELLED;
    }
}
