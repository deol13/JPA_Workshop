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
    private long id;
    @Column(unique = true, nullable = false)
    @UuidGenerator
    private String isbn;
    @Column(nullable = false, length = 100)
    private String title;
    private int maxLoadDays;

    // In ManyToMany and ManyToOne / OneToMany, including the other table in ToString and Equal and HashCode
    // can cause exceptions because it will loop when checking the other side and finding itself.
    @ManyToMany(mappedBy = "writtenBooks")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Setter(AccessLevel.NONE)
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
