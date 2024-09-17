package ma.maroc.echecs.chessportal.repository;

import ma.maroc.echecs.chessportal.model.Membership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MembershipRepository extends JpaRepository<Membership, Long> {
    // Custom query methods (if needed) can be added here
}
