package shaimaa.clinic.appointments.internal.shell.publishers;

import org.springframework.stereotype.Component;
import shaimaa.clinic.appointments.internal.core.contracts.AppointmentsPublisher;
import shaimaa.clinic.shared.domain.DomainEvent;
import shaimaa.clinic.shared.domain.EventBusImpl;

import java.util.List;

@Component
public class AppointmentsPublisherImpl implements AppointmentsPublisher {
    private final EventBusImpl eventBus;

    AppointmentsPublisherImpl(EventBusImpl eventBus) {
        this.eventBus = eventBus;
    }

    public void publish(List<DomainEvent> events) {
        events.stream()
                .map(AppointmentEventsMapper::map)
                .forEach(this.eventBus::push);
    }
}
