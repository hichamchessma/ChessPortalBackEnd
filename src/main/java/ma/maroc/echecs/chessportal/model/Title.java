package ma.maroc.echecs.chessportal.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@AllArgsConstructor
public class Title {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;

    @Column(nullable = false, length = 50)
    private String title; // Example: "Grandmaster", "International Master"

    @Column(nullable = false)
    private LocalDate awardedDate;

    private LocalDate createdAt;
    private LocalDate updatedAt;

    // Getters and Setters
}
