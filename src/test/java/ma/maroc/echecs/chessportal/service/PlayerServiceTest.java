package ma.maroc.echecs.chessportal.service;

import ma.maroc.echecs.chessportal.model.Player;
import ma.maroc.echecs.chessportal.repository.PlayerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlayerServiceTest {


    @Mock
    private PlayerRepository playerRepository;

    @InjectMocks
    private PlayerService playerService;

    private Player player;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        player = new Player(1L, "John", "Doe", LocalDate.of(1990, 1, 1), 2000, 1500, 1800, null, null, null);
    }


    @Test
    void testGetAllPlayers() {
        // Arrange
        List<Player> players = List.of(player);
        when(playerRepository.findAll()).thenReturn(players);

        // Act
       // List<Player> result = (List<Player>) playerService.getAllPlayers();

        // Assert
     //   assertEquals(1, result.size());
     //   assertEquals("John", result.get(0).getFirstName());
    }

    @Test
    void testGetPlayerById() {
        // Arrange
        when(playerRepository.findById(1L)).thenReturn(Optional.of(player));

        // Act
        Optional<Player> result = playerService.getPlayerById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("John", result.get().getFirstName());
    }

    @Test
    void testAddPlayer() {
        // Arrange
        when(playerRepository.save(any(Player.class))).thenReturn(player);

        // Act
        Player result = playerService.addPlayer(player);

        // Assert
        assertNotNull(result);
        assertEquals("John", result.getFirstName());
    }

    @Test
    void testUpdatePlayer() {
        // Arrange
        Player updatedPlayer = new Player(1L, "John", "Smith", LocalDate.of(1990, 1, 1), 2200, 1700, 1900, null, null, null);
        when(playerRepository.findById(1L)).thenReturn(Optional.of(player));
        when(playerRepository.save(any(Player.class))).thenReturn(updatedPlayer);

        // Act
        Player result = playerService.updatePlayer(1L, updatedPlayer);

        // Assert
        assertNotNull(result);
        assertEquals("Smith", result.getLastName());
        assertEquals(2200, result.getEloRating());
    }

    @Test
    void testDeletePlayer() {
        // Arrange
        doNothing().when(playerRepository).deleteById(1L);

        // Act
        playerService.deletePlayer(1L);

        // Assert
        verify(playerRepository, times(1)).deleteById(1L);
    }
}

