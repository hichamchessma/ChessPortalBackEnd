package ma.maroc.echecs.chessportal.repository;

import ma.maroc.echecs.chessportal.model.RatingHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingHistoryRepository extends JpaRepository<RatingHistory, Long> {
    // Custom query methods (if needed) can be added here
}
