package shaimaa.clinic.booking.internal.infrastructure.repositories;

import shaimaa.clinic.booking.internal.domain.events.SlotBooked;
import shaimaa.clinic.booking.shared.dtos.SlotBookedDto;
import shaimaa.clinic.shared.domain.DomainEvent;

// Translates internal domain events into the public DTOs other modules subscribe to.
class BookingEventsMapper {
    static Object map(DomainEvent event) {
        if (event instanceof SlotBooked e) {
            return new SlotBookedDto(
                    e.slotId(),
                    e.slotStartsAt(),
                    e.slotCost(),
                    e.patientId()
            );
        }

        throw new IllegalArgumentException("Unknown booking event: " + event.getClass().getSimpleName());
    }
}
