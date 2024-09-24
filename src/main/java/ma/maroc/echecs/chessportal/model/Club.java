package ma.maroc.echecs.chessportal.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
public class Club {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 100)
    private String city;

    private int creationYear;

    private String contactEmail;

    @OneToMany(mappedBy = "club")
    @JsonIgnore
    private List<Player> players;

    private LocalDate createdAt;
    private LocalDate updatedAt;

    public Club() {
    }
}
