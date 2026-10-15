package shaimaa.clinic.booking.internal.domain.contracts;

import shaimaa.clinic.booking.internal.domain.model.Slot;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SlotRepository {
    void save(Slot slot);
    List<Slot> getAvailableSlots();
    Optional<Slot> getSlotById(UUID slotId);
    void delete(Slot slot);
}
