package ma.maroc.echecs.chessportal.service;

import ma.maroc.echecs.chessportal.model.Tournament;
import ma.maroc.echecs.chessportal.repository.TournamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TournamentService {

    @Autowired
    private TournamentRepository tournamentRepository;

    // Get all tournaments
    public List<Tournament> getAllTournaments() {
        return tournamentRepository.findAll();
    }

    // Get a tournament by its ID
    public Optional<Tournament> getTournamentById(Long id) {
        return tournamentRepository.findById(id);
    }

    // Add a new tournament
    public Tournament addTournament(Tournament tournament) {
        return tournamentRepository.save(tournament);
    }

    // Update an existing tournament
    public Tournament updateTournament(Long id, Tournament updatedTournament) {
        return tournamentRepository.findById(id).map(tournament -> {
            tournament.setName(updatedTournament.getName());
            tournament.setLocation(updatedTournament.getLocation());
            tournament.setStartDate(updatedTournament.getStartDate());
            tournament.setEndDate(updatedTournament.getEndDate());
            tournament.setType(updatedTournament.getType());
            return tournamentRepository.save(tournament);
        }).orElse(null);
    }

    // Delete a tournament
    public void deleteTournament(Long id) {
        tournamentRepository.deleteById(id);
    }
}
