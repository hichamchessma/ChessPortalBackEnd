package ma.maroc.echecs.chessportal.repository;

import ma.maroc.echecs.chessportal.model.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
    // Custom query methods (if needed) can be added here
}
