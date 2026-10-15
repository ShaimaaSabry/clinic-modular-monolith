package shaimaa.clinic.booking.internal.infrastructure.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import shaimaa.clinic.booking.internal.domain.contracts.SlotRepository;
import shaimaa.clinic.booking.internal.domain.model.Slot;
import shaimaa.clinic.shared.domain.EventBusImpl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component("bookingSlotSqlRepository")
class SlotSqlRepository implements SlotRepository {
    @PersistenceContext(unitName = "booking")
    private EntityManager entityManager;

    private final TransactionTemplate transactionTemplate;
    private final EventBusImpl eventBus;

    SlotSqlRepository(
            @Qualifier("bookingTransactionManager") PlatformTransactionManager transactionManager,
            EventBusImpl eventBus
    ) {
        this.transactionTemplate = new TransactionTemplate(transactionManager);
        this.eventBus = eventBus;
    }

    @Override
    @Transactional(transactionManager = "bookingTransactionManager")
    public void save(Slot slot) {
        SlotEntity slotEntity = SlotEntity.from(
                slot.getId(),
                slot.getStartsAt(),
                slot.getCost()
        );

        this.entityManager.persist(slotEntity);
    }

    @Override
    public List<Slot> getAvailableSlots() {
        String sql = "%s %s".formatted(
                "SELECT *",
                "FROM slot"
        );

        List<SlotEntity> slotEntities = entityManager.createNativeQuery(sql, SlotEntity.class).getResultList();

        return slotEntities
                .stream()
                .map(SlotEntity::toDomain)
                .toList();
    }

    @Override
    public Optional<Slot> getSlotById(UUID slotId) {
        SlotEntity slotEntity = this.entityManager.find(SlotEntity.class, slotId);
        return Optional.ofNullable(
                slotEntity.toDomain()
        );
    }

    @Override
    public void delete(Slot slot) {
        this.transactionTemplate.executeWithoutResult(status -> {
            SlotEntity slotEntity = this.entityManager.find(SlotEntity.class, slot.getId());

            this.entityManager.remove(slotEntity);
        });

        // Pushed only after the transaction has committed, so other modules never react to a change that was rolled back.
        slot.pullEvents()
                .stream()
                .map(BookingEventsMapper::map)
                .forEach(this.eventBus::push);
    }
}
