package ma.maroc.echecs.chessportal.controller;

import ma.maroc.echecs.chessportal.config.SecurityConfig;
import ma.maroc.echecs.chessportal.model.RatingHistory;
import ma.maroc.echecs.chessportal.service.RatingHistoryService;
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

@WebMvcTest(RatingHistoryController.class)
@Import(SecurityConfig.class) // If you have a SecurityConfig class
@AutoConfigureMockMvc(addFilters = false) // This will disable Spring Security filters
public class RatingHistoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RatingHistoryService ratingHistoryService;

    private RatingHistory ratingHistory;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ratingHistory = new RatingHistory(1L, null, "Classical", 2400, LocalDate.now(), LocalDate.now(), LocalDate.now());
    }

    @Test
    void testGetAllRatingHistories() throws Exception {
        when(ratingHistoryService.getAllRatingHistories()).thenReturn(Arrays.asList(ratingHistory));

        mockMvc.perform(get("/api/rating-history")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].ratingType").value("Classical"));

        verify(ratingHistoryService, times(1)).getAllRatingHistories();
    }

    @Test
    void testGetRatingHistoryById() throws Exception {
        when(ratingHistoryService.getRatingHistoryById(1L)).thenReturn(Optional.of(ratingHistory));

        mockMvc.perform(get("/api/rating-history/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ratingType").value("Classical"));

        verify(ratingHistoryService, times(1)).getRatingHistoryById(1L);
    }

    @Test
    void testAddRatingHistory() throws Exception {
        when(ratingHistoryService.addRatingHistory(any(RatingHistory.class))).thenReturn(ratingHistory);

        mockMvc.perform(post("/api/rating-history")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"ratingType\": \"Classical\", \"ratingValue\": 2400, \"changeDate\": \"2023-09-01\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ratingType").value("Classical"));

        verify(ratingHistoryService, times(1)).addRatingHistory(any(RatingHistory.class));
    }

    @Test
    void testUpdateRatingHistory() throws Exception {
        when(ratingHistoryService.updateRatingHistory(eq(1L), any(RatingHistory.class))).thenReturn(ratingHistory);

        mockMvc.perform(put("/api/rating-history/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"ratingType\": \"Blitz\", \"ratingValue\": 2300, \"changeDate\": \"2023-09-01\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ratingType").value("Classical"));

        verify(ratingHistoryService, times(1)).updateRatingHistory(eq(1L), any(RatingHistory.class));
    }

    @Test
    void testDeleteRatingHistory() throws Exception {
        doNothing().when(ratingHistoryService).deleteRatingHistory(1L);

        mockMvc.perform(delete("/api/rating-history/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(ratingHistoryService, times(1)).deleteRatingHistory(1L);
    }
}
