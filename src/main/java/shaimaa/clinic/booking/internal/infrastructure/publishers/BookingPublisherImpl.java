package shaimaa.clinic.booking.internal.infrastructure.publishers;

import org.springframework.stereotype.Component;
import shaimaa.clinic.booking.internal.domain.contracts.BookingPublisher;
import shaimaa.clinic.shared.domain.DomainEvent;
import shaimaa.clinic.shared.domain.EventBusImpl;

import java.util.List;

@Component
public class BookingPublisherImpl implements BookingPublisher {
    private final EventBusImpl eventBus;

    BookingPublisherImpl(EventBusImpl eventBus) {
        this.eventBus = eventBus;
    }

    public void publish(List<DomainEvent> events) {
        events.stream()
                .map(BookingEventsMapper::map)
                .forEach(this.eventBus::push);
    }
}
