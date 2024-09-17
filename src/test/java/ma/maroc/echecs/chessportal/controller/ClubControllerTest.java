package ma.maroc.echecs.chessportal.controller;

import ma.maroc.echecs.chessportal.config.SecurityConfig;
import ma.maroc.echecs.chessportal.model.Club;
import ma.maroc.echecs.chessportal.service.ClubService;
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

@WebMvcTest(ClubController.class)
@Import(SecurityConfig.class) // If you have a SecurityConfig class
@AutoConfigureMockMvc(addFilters = false) // This will disable Spring Security filters
public class ClubControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClubService clubService;

    private Club club;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        club = new Club(1L, "Chess Club", "Casablanca", 1995, "info@chessclub.com", Arrays.asList(), LocalDate.now(), LocalDate.now());
    }

    @Test
    void testGetAllClubs() throws Exception {
        when(clubService.getAllClubs()).thenReturn(Arrays.asList(club));

        mockMvc.perform(get("/api/clubs").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(jsonPath("$[0].name").value("Chess Club"));

        verify(clubService, times(1)).getAllClubs();
    }

    @Test
    void testGetClubById() throws Exception {
        when(clubService.getClubById(1L)).thenReturn(Optional.of(club));

        mockMvc.perform(get("/api/clubs/1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(jsonPath("$.name").value("Chess Club"));

        verify(clubService, times(1)).getClubById(1L);
    }

    @Test
    void testAddClub() throws Exception {
        when(clubService.addClub(any(Club.class))).thenReturn(club);

        mockMvc.perform(post("/api/clubs").contentType(MediaType.APPLICATION_JSON).content("{\"name\": \"Chess Club\", \"city\": \"Casablanca\", \"creationYear\": 1995, \"contactEmail\": \"info@chessclub.com\"}")).andExpect(status().isOk()).andExpect(jsonPath("$.name").value("Chess Club"));

        verify(clubService, times(1)).addClub(any(Club.class));
    }

    @Test
    void testUpdateClub() throws Exception {
        when(clubService.updateClub(eq(1L), any(Club.class))).thenReturn(club);

        mockMvc.perform(put("/api/clubs/1").contentType(MediaType.APPLICATION_JSON).content("{\"name\": \"Updated Club\", \"city\": \"Rabat\", \"creationYear\": 2000, \"contactEmail\": \"contact@updatedclub.com\"}")).andExpect(status().isOk()).andExpect(jsonPath("$.name").value("Chess Club"));

        verify(clubService, times(1)).updateClub(eq(1L), any(Club.class));
    }

    @Test
    void testDeleteClub() throws Exception {
        doNothing().when(clubService).deleteClub(1L);

        mockMvc.perform(delete("/api/clubs/1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

        verify(clubService, times(1)).deleteClub(1L);
    }
}
