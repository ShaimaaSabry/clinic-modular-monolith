package shaimaa.clinic.booking.internal.domain.model;

import lombok.Getter;
import shaimaa.clinic.booking.internal.domain.events.SlotBooked;
import shaimaa.clinic.shared.domain.AggregateRoot;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class Slot extends AggregateRoot {
    private UUID id;

    private LocalDateTime startsAt;

    private Float cost;

    private Slot(
            UUID id,
            LocalDateTime startsAt,
            Float cost
    ) {
        this.id = id;
        this.startsAt = startsAt;
        this.cost = cost;
    }

    public static Slot newSlot(
            UUID id,
            LocalDateTime startsAt,
            Float cost
    ) {
        return new Slot(
                id,
                startsAt,
                cost
        );
    }

    public void book(UUID patientId) {
        raiseEvent(new SlotBooked(
                this.id,
                this.startsAt,
                this.cost,
                patientId
        ));
    }

    public static Slot from(
            UUID id,
            LocalDateTime startsAt,
            Float cost
    ) {
        return new Slot(
                id,
                startsAt,
                cost
        );
    }
}
