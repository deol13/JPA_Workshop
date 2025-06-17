package se.lexicon.jpa_workshop.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private int id;
    @Column(unique = true, nullable = false)
    @UuidGenerator
    private String isbn;
    @Column(nullable = false)
    private String title;
    private int maxLoadDays;

    @ManyToMany(mappedBy = "writtenBooks")
    private Set<Author> authors = new HashSet<>();

    public Book(String title, int maxLoadDays) {
        this.title = title;
        this.maxLoadDays = maxLoadDays;
    }

    public void addAuthor(Author author) {
        this.authors.add(author);
        author.getWrittenBooks().add(this);
    }
    public void removeAuthor(Author author) {
        this.authors.remove(author);
        author.getWrittenBooks().remove(this);
    }
}
