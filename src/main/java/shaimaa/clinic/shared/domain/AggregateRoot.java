package shaimaa.clinic.shared.domain;

import java.util.ArrayList;
import java.util.List;

public abstract class AggregateRoot {
    private final List<DomainEvent> occurredEvents = new ArrayList<>();

    protected void raiseEvent(DomainEvent event) {
        this.occurredEvents.add(event);
    }

    // Returns the events raised since the last call and clears them, so each event is pushed only once.
    public List<DomainEvent> pullEvents() {
        List<DomainEvent> events = new ArrayList<>(this.occurredEvents);
        this.occurredEvents.clear();
        return events;
    }
}
