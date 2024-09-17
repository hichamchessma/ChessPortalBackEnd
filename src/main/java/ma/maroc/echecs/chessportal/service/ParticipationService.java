package ma.maroc.echecs.chessportal.service;

import ma.maroc.echecs.chessportal.model.Participation;
import ma.maroc.echecs.chessportal.repository.ParticipationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParticipationService {

    private final ParticipationRepository participationRepository;

    public ParticipationService(ParticipationRepository participationRepository) {
        this.participationRepository = participationRepository;
    }

    public List<Participation> getAllParticipations() {
        return participationRepository.findAll();
    }

    public Optional<Participation> getParticipationById(Long id) {
        return participationRepository.findById(id);
    }

    public Participation addParticipation(Participation participation) {
        return participationRepository.save(participation);
    }

    public Participation updateParticipation(Long id, Participation updatedParticipation) {
        return participationRepository.findById(id)
                .map(participation -> {
                    participation.setPlayer(updatedParticipation.getPlayer());
                    participation.setTournament(updatedParticipation.getTournament());
                    participation.setResult(updatedParticipation.getResult());
                    participation.setRound(updatedParticipation.getRound());
                    return participationRepository.save(participation);
                })
                .orElse(null);
    }

    public void deleteParticipation(Long id) {
        participationRepository.deleteById(id);
    }
}
