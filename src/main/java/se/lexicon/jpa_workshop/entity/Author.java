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

    /*
    Note that using @JoinTable or even @JoinColumn isn’t required.
    JPA will generate the table and column names for us.
    However, the strategy JPA uses won’t always match the naming conventions we use.
    So, we need the possibility to configure table and column names.

    Keep in mind that since a many-to-many relationship doesn’t have an owner side in the database,
    we could configure the join table in the Course class and reference it from the Student class.
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable( name = "authors_books"
            ,joinColumns = @JoinColumn(name = "author_id")
            ,inverseJoinColumns = @JoinColumn(name = "book_id"))
    private Set<Book> writtenBooks = new HashSet<>();

    public Author(String firstName, String lastName, LocalDate birthDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
    }

    public void addWrittenBook(Book book) {
        writtenBooks.add(book);
       // book.getAuthors().add(this);
    }
    public void removeWrittenBook(Book book) {
        writtenBooks.remove(book);
      //  book.getAuthors().remove(this);
    }
}
