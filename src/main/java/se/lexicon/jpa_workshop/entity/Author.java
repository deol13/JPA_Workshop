package se.lexicon.jpa_workshop.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode

@Entity
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(length = 50)
    private String firstName;
    @Column(length = 50)
    private String lastName;
    private LocalDate birthDate;

    @ManyToMany
    //@JoinTable( name = "author_book"
    //        ,joinColumns = @JoinColumn(name = "author_id")
    //        ,inverseJoinColumns = @JoinColumn(name = "book_id"))
    Set<Book> writtenBooks = new HashSet<>();

    public Author(String firstName, String lastName, LocalDate birthDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
    }

    public void addWrittenBook(Book book) {
        writtenBooks.add(book);
        book.getAuthors().add(this);
    }
    public void removeWrittenBook(Book book) {
        writtenBooks.remove(book);
        book.getAuthors().remove(this);
    }
}
