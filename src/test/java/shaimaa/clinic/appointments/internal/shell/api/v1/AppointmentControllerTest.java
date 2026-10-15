package shaimaa.clinic.appointments.internal.shell.api.v1;

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
import shaimaa.clinic.appointments.internal.core.model.Appointment;
import shaimaa.clinic.appointments.internal.core.model.AppointmentStatus;
import shaimaa.clinic.appointments.internal.core.model.Patient;
import shaimaa.clinic.appointments.internal.core.services.AppointmentService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AppointmentController.class)
@Import(AppointmentControllerTest.MockBeans.class)
class AppointmentControllerTest {

    @TestConfiguration
    static class MockBeans {
        @Bean
        AppointmentService appointmentService() {
            return Mockito.mock(AppointmentService.class);
        }
    }

    @Autowired
    private AppointmentService mockAppointmentService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Nested
    class GetUpcomingAppointments {
        @Test
        void shouldReturn200AndAppointments() throws Exception {
            // given
            Appointment appointment = createValidAppointment();

            Mockito.when(mockAppointmentService.getUpcomingAppointments()).thenReturn(List.of(appointment));

            // when
            // then
            mockMvc.perform(MockMvcRequestBuilders
                            .get("/v1/appointments/upcoming"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.appointments").isArray())
                    .andExpect(jsonPath("$.appointments.length()").value(1))
                    .andExpect(jsonPath("$.appointments[0].id").value(appointment.getId().toString()))
                    .andExpect(jsonPath("$.appointments[0].startsAt").value(
                            appointment.getSlotStartsAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
                            )))
                    .andExpect(jsonPath("$.appointments[0].patient.id").value(appointment.getPatient().getId().toString()))
                    .andExpect(jsonPath("$.appointments[0].patient.name").value("%s %s".formatted(
                            appointment.getPatient().getFirstName(),
                            appointment.getPatient().getLastName()
                    )));
        }

        private Appointment createValidAppointment() {
            return Appointment.of(
                    UUID.randomUUID(),
                    UUID.randomUUID(),
                    LocalDateTime.now(),
                    250,
                    Patient.of(UUID.randomUUID(), "Yara", "Ali"),
                    AppointmentStatus.SCHEDULED
            );
        }
    }

    @Nested
    class CancelAppointment {
        @Test
        void givenValidAppointmentId_thenShouldReturn204() throws Exception {
            // given
            UUID appointmentId = UUID.randomUUID();

            // when
            // then
            mockMvc.perform(MockMvcRequestBuilders
                            .patch("/v1/appointments/" + appointmentId + "/cancel"))
                    .andExpect(status().isNoContent());
        }
    }

    @Nested
    class CompleteAppointment {
        @Test
        void givenValidAppointmentId_shouldReturn204() throws Exception {
            // given
            UUID appointmentId = UUID.randomUUID();

            // when
            // then
            mockMvc.perform(MockMvcRequestBuilders
                            .patch("/v1/appointments/" + appointmentId + "/complete"))
                    .andExpect(status().isNoContent());
        }
    }
}