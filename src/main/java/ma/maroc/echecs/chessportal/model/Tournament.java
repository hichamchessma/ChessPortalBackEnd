package ma.maroc.echecs.chessportal.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
public class Tournament {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    private String location;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false, length = 50)
    private String type; // Example: "Classical", "Blitz", "Rapid"

    @OneToMany(mappedBy = "tournament")
    private List<Participation> participations;

    private LocalDate createdAt;
    private LocalDate updatedAt;

}
