package ma.maroc.echecs.chessportal.repository;

import ma.maroc.echecs.chessportal.model.Participation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipationRepository extends JpaRepository<Participation, Long> {
    // Custom query methods (if needed) can be added here
}
