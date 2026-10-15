package shaimaa.clinic.booking.internal.domain.events;

import shaimaa.clinic.shared.domain.DomainEvent;

import java.time.LocalDateTime;
import java.util.UUID;

public record SlotBooked(
        UUID slotId,
        LocalDateTime slotStartsAt,
        Float slotCost,
        UUID patientId
) implements DomainEvent {
}
