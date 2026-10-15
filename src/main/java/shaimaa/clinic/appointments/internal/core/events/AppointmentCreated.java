package shaimaa.clinic.appointments.internal.core.events;

import shaimaa.clinic.shared.domain.DomainEvent;

import java.time.LocalDateTime;
import java.util.UUID;

public record AppointmentCreated(
        UUID appointmentId,
        UUID slotId,
        LocalDateTime slotStartsAt,
        float slotCost,
        UUID patientId,
        String patientFirstName,
        String patientLastName
) implements DomainEvent {
}
