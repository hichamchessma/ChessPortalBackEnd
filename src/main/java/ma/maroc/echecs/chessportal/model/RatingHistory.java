package ma.maroc.echecs.chessportal.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@AllArgsConstructor
public class RatingHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;

    @Column(nullable = false, length = 50)
    private String ratingType; // Example: "Classical", "Blitz", "Rapid"

    @Column(nullable = false)
    private int ratingValue;

    @Column(nullable = false)
    private LocalDate changeDate;

    private LocalDate createdAt;
    private LocalDate updatedAt;

    // Getters and Setters
}
