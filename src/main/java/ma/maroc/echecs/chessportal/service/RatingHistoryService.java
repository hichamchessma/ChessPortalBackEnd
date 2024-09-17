package ma.maroc.echecs.chessportal.service;

import ma.maroc.echecs.chessportal.model.RatingHistory;
import ma.maroc.echecs.chessportal.repository.RatingHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class RatingHistoryService {

    @Autowired
    private RatingHistoryRepository ratingHistoryRepository;

    public List<RatingHistory> getAllRatingHistories() {
        return ratingHistoryRepository.findAll();
    }

    public Optional<RatingHistory> getRatingHistoryById(Long id) {
        return ratingHistoryRepository.findById(id);
    }

    public RatingHistory addRatingHistory(RatingHistory ratingHistory) {
        ratingHistory.setCreatedAt(LocalDate.now());
        ratingHistory.setUpdatedAt(LocalDate.now());
        return ratingHistoryRepository.save(ratingHistory);
    }

    public RatingHistory updateRatingHistory(Long id, RatingHistory updatedRatingHistory) {
        return ratingHistoryRepository.findById(id).map(ratingHistory -> {
            ratingHistory.setRatingType(updatedRatingHistory.getRatingType());
            ratingHistory.setRatingValue(updatedRatingHistory.getRatingValue());
            ratingHistory.setChangeDate(updatedRatingHistory.getChangeDate());
            ratingHistory.setUpdatedAt(LocalDate.now());
            return ratingHistoryRepository.save(ratingHistory);
        }).orElse(null);
    }

    public void deleteRatingHistory(Long id) {
        ratingHistoryRepository.deleteById(id);
    }
}
