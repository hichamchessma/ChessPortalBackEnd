package ma.maroc.echecs.chessportal.service;

import ma.maroc.echecs.chessportal.model.Player;
import ma.maroc.echecs.chessportal.model.RatingHistory;
import ma.maroc.echecs.chessportal.repository.RatingHistoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class RatingHistoryServiceTest {

    @Mock
    private RatingHistoryRepository ratingHistoryRepository;

    @InjectMocks
    private RatingHistoryService ratingHistoryService;

    private RatingHistory ratingHistory;

    @BeforeEach
    void setUp() {
        ratingHistory = new RatingHistory(1L, new Player(), "Classical", 2400, LocalDate.of(2024, 9, 1), LocalDate.now(), LocalDate.now());
    }

    @Test
    void testAddRatingHistory() {
        when(ratingHistoryRepository.save(any(RatingHistory.class))).thenReturn(ratingHistory);
        RatingHistory result = ratingHistoryService.addRatingHistory(ratingHistory);
        assertEquals("Classical", result.getRatingType());
    }

    @Test
    void testGetAllRatingHistories() {
        List<RatingHistory> ratingHistories = List.of(ratingHistory);
        when(ratingHistoryRepository.findAll()).thenReturn(ratingHistories);
        List<RatingHistory> result = ratingHistoryService.getAllRatingHistories();
        assertEquals(1, result.size());
        assertEquals("Classical", result.get(0).getRatingType());
    }

    @Test
    void testDeleteRatingHistory() {
        ratingHistoryService.deleteRatingHistory(1L);
        verify(ratingHistoryRepository, times(1)).deleteById(1L);
    }
}
