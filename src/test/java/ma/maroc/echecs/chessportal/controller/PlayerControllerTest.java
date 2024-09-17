package ma.maroc.echecs.chessportal.controller;

import ma.maroc.echecs.chessportal.config.SecurityConfig;
import ma.maroc.echecs.chessportal.model.Player;
import ma.maroc.echecs.chessportal.service.PlayerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PlayerController.class)
@Import(SecurityConfig.class) // If you have a SecurityConfig class
@AutoConfigureMockMvc(addFilters = false) // This will disable Spring Security filters
public class PlayerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlayerService playerService;


    private Player player;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        player = new Player(1L, "John", "Doe", LocalDate.of(1990, 1, 1), 2000, 1500, 1800, null, LocalDate.now(), LocalDate.now());
    }

    @Test
    void testGetAllPlayers() throws Exception {
        when(playerService.getAllPlayers()).thenReturn(Arrays.asList(player));

        mockMvc.perform(get("/api/players")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("John"));

        verify(playerService, times(1)).getAllPlayers();
    }

    @Test
    void testGetPlayerById() throws Exception {
        when(playerService.getPlayerById(1L)).thenReturn(Optional.of(player));

        mockMvc.perform(get("/api/players/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"));

        verify(playerService, times(1)).getPlayerById(1L);
    }

    @Test
    void testAddPlayer() throws Exception {
        when(playerService.addPlayer(any(Player.class))).thenReturn(player);

        mockMvc.perform(post("/api/players")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\": \"John\", \"lastName\": \"Doe\", \"birthdate\": \"1990-01-01\", \"eloRating\": 2000, \"blitzRating\": 1500, \"rapidRating\": 1800}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"));

        verify(playerService, times(1)).addPlayer(any(Player.class));
    }

    @Test
    void testUpdatePlayer() throws Exception {
        when(playerService.updatePlayer(anyLong(), any(Player.class))).thenReturn(player);

        mockMvc.perform(put("/api/players/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\": \"John\", \"lastName\": \"Doe\", \"birthdate\": \"1990-01-01\", \"eloRating\": 2200}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.eloRating").value(2000));

        verify(playerService, times(1)).updatePlayer(anyLong(), any(Player.class));
    }

    @Test
    void testDeletePlayer() throws Exception {
        doNothing().when(playerService).deletePlayer(anyLong());

        mockMvc.perform(delete("/api/players/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(playerService, times(1)).deletePlayer(anyLong());
    }
}
