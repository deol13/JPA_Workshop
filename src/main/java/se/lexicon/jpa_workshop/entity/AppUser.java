package se.lexicon.jpa_workshop.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// Lombok annotations, you can exclude fields. (exclude =)
// Lombok removes a lot of the boiler code (repeated code), such as making constructors and getters / setters for classes
// These annotations marks that Lombok should generate these methods.
@NoArgsConstructor // Creates a default constructor
@AllArgsConstructor // Creates a constructor with all fields
@Getter // Creates getter methods for all fields
@ToString // Creates a toString method with all fields
@EqualsAndHashCode // Creates Equal and Hashcode methods with all fields

// Marks this class as an entity so JPA creates a table in the database of it.
// Also makes a component for Spring Cores IoC and DI
// A field with @Id annotation and a default is needed.
@Entity
public class AppUser {

    // Marks this field as the primary key in this Entity
    // Makes the database generate a value for this variable,
    // GenerationType is different ways to give this field a value, IDENTITY is auto increment
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id; // Follow naming conventions

    // @Setter is a Lombok annotation for auto generate a setter method.
    // We could put @Setter outside the class with all the others
    // but if we just want specific setters and getters to be made, we need to put it before those fields.
    // Here we don't want a setter for id.
    @Column(length = 50)
    @Setter private String username;
    @Column(length = 50)
    @Setter private String password; // don't forgot to hash it
    @Setter private LocalDate regDate;

    // @OneToOne annotation maps a relationship with the class of the object its above for the database.
    // OneToMany and ManyToOne are other relationship annotations.
    // Without adding anything in the other class, this becomes a uni-directional relationship, only works on @OneToOne.
    // We could add a @OneToOne(mappedBy = userDetails) in Details to make it bidirectional relationship.

    // @JoinColumn annotation marks a field as a joined column for a relationship with the class of the object.
    // With @OneToOne annotation it indicates that the given column in the owner entity refers to a primary key in the reference entity.
    // In this case it will create a foreign key linking AppUser with the primary key from Details.
    // "name" in @JoinColumn is the name of the foreign key column this annotation creates by connecting the two classes.

    /*
    ManyToOne/OneToMany - Many side is usually the owning side
    OneToOne - Choose based on use case or data model
    ManyToMany - Pick one side as owning, use mappedBy on the other
     */

    // Details is an integral part of AppUser and is not used on its own, so using CascadeType.ALL to handle it from AppUser make sense
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "details_id")
    @Setter private Details userDetails;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "borrower")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    List<BookLoan> bookLoanList = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        regDate = LocalDate.now();
    }

    public AppUser(String username, String password, Details userDetails) {
        this.username = username;
        this.password = password;
        this.userDetails = userDetails;
    }
    public AppUser(String username, String password ) {
        this.username = username;
        this.password = password;
    }

    public void addBookLoan(BookLoan bookLoan) {
        bookLoanList.add(bookLoan);
        bookLoan.setBorrower(this);
    }
    public void removeBookLoan(BookLoan bookLoan) {
        boolean success = bookLoanList.remove(bookLoan);
        bookLoan.setBorrower(null);
    }

    //todo: checkout Todo_API Todo and Attachment relationship and their methods
}
