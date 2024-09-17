package ma.maroc.echecs.chessportal.service;

import ma.maroc.echecs.chessportal.model.Tournament;
import ma.maroc.echecs.chessportal.repository.TournamentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class TournamentServiceTest {

    @Mock
    private TournamentRepository tournamentRepository;

    @InjectMocks
    private TournamentService tournamentService;

    private Tournament tournament;

    @BeforeEach
    void setUp() {
        tournament = new Tournament(1L, "Casablanca Open", "Casablanca", LocalDate.of(2024, 9, 1), LocalDate.of(2024, 9, 5), "Classical", new ArrayList<>(), LocalDate.now(), LocalDate.now());
    }

    @Test
    void testAddTournament() {
        when(tournamentRepository.save(any(Tournament.class))).thenReturn(tournament);
        Tournament result = tournamentService.addTournament(tournament);
        assertEquals(tournament.getName(), result.getName());
    }

    @Test
    void testGetAllTournaments() {
        List<Tournament> tournaments = List.of(tournament);
        when(tournamentRepository.findAll()).thenReturn(tournaments);
        List<Tournament> result = tournamentService.getAllTournaments();
        assertEquals(1, result.size());
        assertEquals("Casablanca Open", result.get(0).getName());
    }

    @Test
    void testDeleteTournament() {
        tournamentService.deleteTournament(1L);
        verify(tournamentRepository, times(1)).deleteById(1L);
    }
}
