package ma.maroc.echecs.chessportal.controller;

import ma.maroc.echecs.chessportal.config.SecurityConfig;
import ma.maroc.echecs.chessportal.model.Participation;
import ma.maroc.echecs.chessportal.service.ParticipationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ParticipationController.class)
@Import(SecurityConfig.class) // If you have a SecurityConfig class
@AutoConfigureMockMvc(addFilters = false) // This will disable Spring Security filters
public class ParticipationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ParticipationService participationService;

    private Participation participation;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        participation = new Participation(1L, null, null, "Win", 3, LocalDate.now(), LocalDate.now());
    }

    @Test
    void testGetAllParticipations() throws Exception {
        when(participationService.getAllParticipations()).thenReturn(Arrays.asList(participation));

        mockMvc.perform(get("/api/participations")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].result").value("Win"));

        verify(participationService, times(1)).getAllParticipations();
    }

    @Test
    void testGetParticipationById() throws Exception {
        when(participationService.getParticipationById(1L)).thenReturn(Optional.of(participation));

        mockMvc.perform(get("/api/participations/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value("Win"));

        verify(participationService, times(1)).getParticipationById(1L);
    }

    @Test
    void testAddParticipation() throws Exception {
        when(participationService.addParticipation(any(Participation.class))).thenReturn(participation);

        mockMvc.perform(post("/api/participations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"result\": \"Win\", \"round\": 3}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value("Win"));

        verify(participationService, times(1)).addParticipation(any(Participation.class));
    }

    @Test
    void testUpdateParticipation() throws Exception {
        when(participationService.updateParticipation(eq(1L), any(Participation.class))).thenReturn(participation);

        mockMvc.perform(put("/api/participations/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"result\": \"Loss\", \"round\": 4}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value("Win"));

        verify(participationService, times(1)).updateParticipation(eq(1L), any(Participation.class));
    }

    @Test
    void testDeleteParticipation() throws Exception {
        doNothing().when(participationService).deleteParticipation(1L);

        mockMvc.perform(delete("/api/participations/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(participationService, times(1)).deleteParticipation(1L);
    }
}
