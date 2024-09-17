package ma.maroc.echecs.chessportal.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;



@Data
@Entity
@AllArgsConstructor
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String firstName;

    @Column(nullable = false, length = 100)
    private String lastName;

    private LocalDate birthdate;

    private int eloRating;
    private int blitzRating;
    private int rapidRating;

    @ManyToOne
    @JoinColumn(name = "club_id")
    private Club club;

    private LocalDate createdAt = LocalDate.now();

    public Player() {
    }

    private LocalDate updatedAt;

    // Getters and Setters
}
