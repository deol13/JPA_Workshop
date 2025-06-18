package se.lexicon.jpa_workshop.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private int id;
    @Column(unique = true, nullable = false)
    @UuidGenerator
    private String isbn;
    @Column(nullable = false, length = 100)
    private String title;
    private int maxLoadDays;

    public Book(String title, int maxLoadDays) {
        this.title = title;
        this.maxLoadDays = maxLoadDays;
    }
}
