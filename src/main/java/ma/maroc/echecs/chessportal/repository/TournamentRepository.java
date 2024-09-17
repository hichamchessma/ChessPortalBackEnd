package ma.maroc.echecs.chessportal.repository;

import ma.maroc.echecs.chessportal.model.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TournamentRepository extends JpaRepository<Tournament, Long> {
    // Custom query methods (if needed) can be added here
}
