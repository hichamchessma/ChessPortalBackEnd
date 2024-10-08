package ma.maroc.echecs.chessportal.repository;

import ma.maroc.echecs.chessportal.model.Player;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
    Page<Player> findByFirstNameContainingOrLastNameContainingOrClubNameContaining(String firstName, String lastName, String clubName, Pageable pageable);

}
