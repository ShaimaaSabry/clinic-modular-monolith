package shaimaa.clinic.booking.internal.application.commands.BookSlot;

import org.springframework.stereotype.Component;
import shaimaa.clinic.booking.internal.domain.contracts.SlotRepository;
import shaimaa.clinic.booking.internal.domain.model.Slot;

@Component
public class BookSlotHandler {
    private final SlotRepository slotRepository;

    BookSlotHandler(final SlotRepository slotRepository) {
        this.slotRepository = slotRepository;
    }

    public void handle(BookSlotCommand command) {
        Slot slot = this.slotRepository.getSlotById(command.slotId()).get();

        slot.book(command.patientId());

        this.slotRepository.delete(slot);
    }
}
