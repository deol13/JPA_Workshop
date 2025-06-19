package se.lexicon.jpa_workshop.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode

@Entity
public class BookLoan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private LocalDate loanDate;
    @Column(nullable = false)
    private LocalDate dueDate;
    @Column(nullable = false)
    private boolean returned;

    /*
    @OneToOne = FetchType.EAGER
    @OneToMany = FetchType.LAZY
    @ManyToOne =  FetchType.EAGER
    @ManyToMany = FetchType.LAZY
     */

    // Uni directional many-to-to relationship, the associated classes / tables have no information about it.
    // Many of this class / table can have a relationship with the same AppUser and Book.
    // BookLoan and AppUser are separate entities and not very connected so it doesn't make sense to connected them with Cascade.ALL,
    // Using Cascade and not using Cascade affects code differently so be careful when you use Cascade.
    @ManyToOne
    @JoinColumn(name = "borrower_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private AppUser borrower;

    @ManyToOne
    private Book book;

    //This will be called before saving to database, initializing default values to loadDate and returned
    @PrePersist
    public void onCreate() {
        loanDate = LocalDate.now();
        this.returned = false;
    }

    public BookLoan(LocalDate dueDate){
        this.dueDate = dueDate;
    }

    public BookLoan(LocalDate dueDate, Book book){
        this(dueDate);
        this.dueDate = dueDate;
        this.book = book;
    }

    public BookLoan(LocalDate dueDate, AppUser borrower, Book book) {
        this(dueDate, book);
        this.borrower = borrower;
    }
}
