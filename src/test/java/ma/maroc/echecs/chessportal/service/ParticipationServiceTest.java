package ma.maroc.echecs.chessportal.service;

import ma.maroc.echecs.chessportal.model.Participation;
import ma.maroc.echecs.chessportal.repository.ParticipationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class ParticipationServiceTest {

    @Mock
    private ParticipationRepository participationRepository;

    @InjectMocks
    private ParticipationService participationService;

    private Participation participation;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        participation = new Participation(1L, null, null, "Win", 1, LocalDate.now(), LocalDate.now());
    }

    @Test
    void getAllParticipations() {
        when(participationRepository.findAll()).thenReturn(Arrays.asList(participation));
        List<Participation> participations = participationService.getAllParticipations();
        assertEquals(1, participations.size());
        verify(participationRepository, times(1)).findAll();
    }

    @Test
    void getParticipationById() {
        when(participationRepository.findById(1L)).thenReturn(Optional.of(participation));
        Optional<Participation> found = participationService.getParticipationById(1L);
        assertTrue(found.isPresent());
        verify(participationRepository, times(1)).findById(1L);
    }

    @Test
    void addParticipation() {
        when(participationRepository.save(any(Participation.class))).thenReturn(participation);
        Participation created = participationService.addParticipation(participation);
        assertEquals(participation.getId(), created.getId());
        verify(participationRepository, times(1)).save(participation);
    }

    @Test
    void updateParticipation() {
        when(participationRepository.findById(1L)).thenReturn(Optional.of(participation));
        when(participationRepository.save(participation)).thenReturn(participation);
        Participation updated = participationService.updateParticipation(1L, participation);
        assertEquals(participation.getId(), updated.getId());
        verify(participationRepository, times(1)).save(participation);
    }

    @Test
    void deleteParticipation() {
        participationService.deleteParticipation(1L);
        verify(participationRepository, times(1)).deleteById(1L);
    }
}
