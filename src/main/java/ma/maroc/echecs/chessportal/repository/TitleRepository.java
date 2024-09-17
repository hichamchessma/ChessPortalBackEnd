package ma.maroc.echecs.chessportal.repository;

import ma.maroc.echecs.chessportal.model.Title;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TitleRepository extends JpaRepository<Title, Long> {
    // Custom query methods (if needed) can be added here
}
