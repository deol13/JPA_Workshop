package se.lexicon.jpa_workshop.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true, nullable = false)
    @UuidGenerator
    private String isbn;
    @Column(nullable = false)
    private String title;
    private int maxLoadDays;

    public Book(String title, int maxLoadDays) {
        this.title = title;
        this.maxLoadDays = maxLoadDays;
    }
}
