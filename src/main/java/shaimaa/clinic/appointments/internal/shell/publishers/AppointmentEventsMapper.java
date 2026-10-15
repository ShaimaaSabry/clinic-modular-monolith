package shaimaa.clinic.appointments.internal.shell.publishers;

import shaimaa.clinic.appointments.internal.core.events.AppointmentCreated;
import shaimaa.clinic.appointments.shared.dtos.AppointmentCreatedDto;
import shaimaa.clinic.shared.domain.DomainEvent;

// Translates internal domain events into the public DTOs other modules subscribe to.
class AppointmentEventsMapper {
    static Object map(DomainEvent event) {
        if (event instanceof AppointmentCreated e) {
            return new AppointmentCreatedDto(
                    e.slotId(),
                    e.slotStartsAt(),
                    e.slotCost(),
                    e.patientId(),
                    e.patientFirstName(),
                    e.patientLastName()
            );
        }

        throw new IllegalArgumentException("Unknown appointments event: " + event.getClass().getSimpleName());
    }
}
