package shaimaa.clinic.appointments.internal.core.model;

import org.junit.jupiter.api.Test;
import shaimaa.clinic.appointments.internal.core.events.AppointmentCreated;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class AppointmentTest {
    @Test
    void givenNewAppointment_thenRaisesAppointmentCreated() {
        UUID slotId = UUID.randomUUID();
        UUID patientId = UUID.randomUUID();
        LocalDateTime startsAt = LocalDateTime.now();

        Appointment appointment = Appointment.newAppointment(slotId, startsAt, 250, patientId, "Yara", "Ali");

        assertThat(appointment.pullEvents()).containsExactly(
                new AppointmentCreated(appointment.getId(), slotId, startsAt, 250, patientId, "Yara", "Ali")
        );
    }

    @Test
    void givenExistingAppointment_whenCancelled_thenRaisesNoEvents() {
        Appointment appointment = Appointment.of(
                UUID.randomUUID(), UUID.randomUUID(), LocalDateTime.now(), 250,
                Patient.of(UUID.randomUUID(), "Yara", "Ali"), AppointmentStatus.SCHEDULED
        );

        appointment.cancel();

        assertThat(appointment.pullEvents()).isEmpty();
    }
}
