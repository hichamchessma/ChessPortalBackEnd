package ma.maroc.echecs.chessportal.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@AllArgsConstructor
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    private String author;

    @Column(nullable = false)
    private LocalDate publishedDate;

    private LocalDate createdAt;
    private LocalDate updatedAt;

    // Getters and Setters
}
