package ma.maroc.echecs.chessportal.controller;

import ma.maroc.echecs.chessportal.model.RatingHistory;
import ma.maroc.echecs.chessportal.service.RatingHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/rating-history")
public class RatingHistoryController {

    private final RatingHistoryService ratingHistoryService;

    @Autowired
    public RatingHistoryController(RatingHistoryService ratingHistoryService) {
        this.ratingHistoryService = ratingHistoryService;
    }

    @GetMapping
    public List<RatingHistory> getAllRatingHistories() {
        return ratingHistoryService.getAllRatingHistories();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RatingHistory> getRatingHistoryById(@PathVariable Long id) {
        Optional<RatingHistory> ratingHistory = ratingHistoryService.getRatingHistoryById(id);
        return ratingHistory.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public RatingHistory addRatingHistory(@RequestBody RatingHistory ratingHistory) {
        return ratingHistoryService.addRatingHistory(ratingHistory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RatingHistory> updateRatingHistory(@PathVariable Long id, @RequestBody RatingHistory updatedRatingHistory) {
        Optional<RatingHistory> updated = Optional.ofNullable(ratingHistoryService.updateRatingHistory(id, updatedRatingHistory));
        return updated.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRatingHistory(@PathVariable Long id) {
        ratingHistoryService.deleteRatingHistory(id);
        return ResponseEntity.noContent().build();
    }
}
