package ma.maroc.echecs.chessportal.controller;

import ma.maroc.echecs.chessportal.config.SecurityConfig;
import ma.maroc.echecs.chessportal.model.Tournament;
import ma.maroc.echecs.chessportal.service.TournamentService;
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

@WebMvcTest(TournamentController.class)
@Import(SecurityConfig.class) // If you have a SecurityConfig class
@AutoConfigureMockMvc(addFilters = false) // This will disable Spring Security filters
public class TournamentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TournamentService tournamentService;

    private Tournament tournament;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        tournament = new Tournament(1L, "Chess Masters", "Casablanca", LocalDate.of(2023, 9, 1), LocalDate.of(2023, 9, 10), "Classical", null, LocalDate.now(), LocalDate.now());
    }

    @Test
    void testGetAllTournaments() throws Exception {
        when(tournamentService.getAllTournaments()).thenReturn(Arrays.asList(tournament));

        mockMvc.perform(get("/api/tournaments")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Chess Masters"));

        verify(tournamentService, times(1)).getAllTournaments();
    }

    @Test
    void testGetTournamentById() throws Exception {
        when(tournamentService.getTournamentById(1L)).thenReturn(Optional.of(tournament));

        mockMvc.perform(get("/api/tournaments/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Chess Masters"));

        verify(tournamentService, times(1)).getTournamentById(1L);
    }

    @Test
    void testAddTournament() throws Exception {
        when(tournamentService.addTournament(any(Tournament.class))).thenReturn(tournament);

        mockMvc.perform(post("/api/tournaments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Chess Masters\", \"location\": \"Casablanca\", \"startDate\": \"2023-09-01\", \"endDate\": \"2023-09-10\", \"type\": \"Classical\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Chess Masters"));

        verify(tournamentService, times(1)).addTournament(any(Tournament.class));
    }

    @Test
    void testUpdateTournament() throws Exception {
        when(tournamentService.updateTournament(eq(1L), any(Tournament.class))).thenReturn(tournament);

        mockMvc.perform(put("/api/tournaments/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Chess Open\", \"location\": \"Rabat\", \"startDate\": \"2023-09-05\", \"endDate\": \"2023-09-15\", \"type\": \"Rapid\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Chess Masters"));

        verify(tournamentService, times(1)).updateTournament(eq(1L), any(Tournament.class));
    }

    @Test
    void testDeleteTournament() throws Exception {
        doNothing().when(tournamentService).deleteTournament(1L);

        mockMvc.perform(delete("/api/tournaments/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(tournamentService, times(1)).deleteTournament(1L);
    }
}
