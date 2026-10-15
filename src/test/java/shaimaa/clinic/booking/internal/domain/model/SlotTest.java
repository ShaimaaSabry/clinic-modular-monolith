package shaimaa.clinic.booking.internal.domain.model;

import org.junit.jupiter.api.Test;
import shaimaa.clinic.booking.internal.domain.events.SlotBooked;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class SlotTest {
    @Test
    void givenSlot_whenBooked_thenRaisesSlotBooked() {
        Slot slot = Slot.from(UUID.randomUUID(), LocalDateTime.now(), 250f);
        UUID patientId = UUID.randomUUID();

        slot.book(patientId);

        assertThat(slot.pullEvents()).containsExactly(
                new SlotBooked(slot.getId(), slot.getStartsAt(), slot.getCost(), patientId)
        );
    }

    @Test
    void givenEventsPulled_whenPulledAgain_thenEmpty() {
        Slot slot = Slot.from(UUID.randomUUID(), LocalDateTime.now(), 250f);
        slot.book(UUID.randomUUID());

        slot.pullEvents();

        assertThat(slot.pullEvents()).isEmpty();
    }
}
