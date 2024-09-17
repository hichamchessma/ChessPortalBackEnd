package ma.maroc.echecs.chessportal.service;
import ma.maroc.echecs.chessportal.model.Club;
import ma.maroc.echecs.chessportal.repository.ClubRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ClubServiceTest {

    @Mock
    private ClubRepository clubRepository;

    @InjectMocks
    private ClubService clubService;

    private Club club;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        club = new Club(1L, "Chess Club", "City", 1990, "email@domain.com",
                null, LocalDate.now(), LocalDate.now());
    }

    @Test
    void testGetAllClubs() {
        List<Club> clubs = List.of(club);

        when(clubRepository.findAll()).thenReturn(clubs);

        List<Club> result = clubService.getAllClubs();
        assertEquals(1, result.size());
        assertEquals("Chess Club", result.get(0).getName());

        verify(clubRepository, times(1)).findAll();
    }

    @Test
    void testGetClubById() {
        when(clubRepository.findById(1L)).thenReturn(Optional.of(club));

        Optional<Club> result = clubService.getClubById(1L);
        assertTrue(result.isPresent());
        assertEquals("Chess Club", result.get().getName());

        verify(clubRepository, times(1)).findById(1L);
    }

    @Test
    void testAddClub() {
        when(clubRepository.save(any(Club.class))).thenReturn(club);

        Club result = clubService.addClub(club);
        assertEquals("Chess Club", result.getName());

        verify(clubRepository, times(1)).save(club);
    }

    @Test
    void testUpdateClub() {
        when(clubRepository.findById(1L)).thenReturn(Optional.of(club));
        when(clubRepository.save(any(Club.class))).thenReturn(club);

        Club updatedClub = new Club(1L, "Updated Chess Club", "Updated City", 2000, "updated@domain.com",
                null, LocalDate.now(), LocalDate.now());

        Club result = clubService.updateClub(1L, updatedClub);
        assertEquals("Updated Chess Club", result.getName());

        verify(clubRepository, times(1)).findById(1L);
        verify(clubRepository, times(1)).save(club);
    }

    @Test
    void testDeleteClub() {
        doNothing().when(clubRepository).deleteById(1L);

        clubService.deleteClub(1L);

        verify(clubRepository, times(1)).deleteById(1L);
    }
}
