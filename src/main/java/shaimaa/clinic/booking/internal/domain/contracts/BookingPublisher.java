package shaimaa.clinic.booking.internal.domain.contracts;

import shaimaa.clinic.shared.domain.DomainEvent;

import java.util.List;

public interface BookingPublisher {
    void publish(List<DomainEvent> events);
}
