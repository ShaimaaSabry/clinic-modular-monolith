package shaimaa.clinic.appointments.internal.core.contracts;

import shaimaa.clinic.shared.domain.DomainEvent;

import java.util.List;

public interface AppointmentsPublisher {
    void publish(List<DomainEvent> events);
}
