package ma.maroc.echecs.chessportal.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@AllArgsConstructor
public class Participation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;

    @ManyToOne
    @JoinColumn(name = "tournament_id")
    private Tournament tournament;

    private String result; // Example: "Win", "Loss", "Draw"

    private int round;

    private LocalDate createdAt;
    private LocalDate updatedAt;

    public Participation() {
    }
}
