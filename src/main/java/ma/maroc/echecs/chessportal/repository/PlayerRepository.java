package ma.maroc.echecs.chessportal.repository;

import ma.maroc.echecs.chessportal.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
    // Custom query methods (if needed) can be added here
}
