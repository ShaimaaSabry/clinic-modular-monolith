package shaimaa.clinic.booking.internal.presentation.api.v1.GetAvailableSlots;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import shaimaa.clinic.booking.internal.application.queries.GetAvailableSlots.GetAvailableSlotsHandler;
import shaimaa.clinic.booking.internal.domain.model.Slot;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.closeTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GetAvailableSlotsController.class)
@Import(GetAvailableSlotsControllerTest.MockBeans.class)
class GetAvailableSlotsControllerTest {

    @TestConfiguration
    static class MockBeans {
        @Bean
        GetAvailableSlotsHandler getAvailableSlotsHandler() {
            return Mockito.mock(GetAvailableSlotsHandler.class);
        }
    }

    @Autowired
    private GetAvailableSlotsHandler mockGetAvailableSlotsHandler;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Nested
    class GetAvailableSlots {
        @Test
        void shouldReturn200AndSlots() throws Exception {
            // given
            Slot slot = createValidSlot();

            Mockito.when(mockGetAvailableSlotsHandler.handle()).thenReturn(List.of(slot));

            // when
            // then
            mockMvc.perform(MockMvcRequestBuilders
                            .get("/v1/slots/available"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.slots").isArray())
                    .andExpect(jsonPath("$.slots.length()").value(1))
                    .andExpect(jsonPath("$.slots[0].id").value(slot.getId().toString()))
                    .andExpect(jsonPath("$.slots[0].startsAt").value(
                            slot.getStartsAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
                    )))
                    .andExpect(jsonPath("$.slots[0].cost", closeTo(slot.getCost(), 0.01f)));
        }

        private Slot createValidSlot() {
            return Slot.from(
                    UUID.randomUUID(),
                    LocalDateTime.now(),
                    250f
            );
        }
    }
}
