package se.lexicon.jpa_workshop.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

// Lombok annotations, you can exclude fields. (exclude =)
@NoArgsConstructor // Creates a default constructor
@AllArgsConstructor // Creates a constructor with all fields
@Getter // Creates getter methods for all fields
@ToString // Creates a toString method with all fields
@EqualsAndHashCode // Creates Equal and Hashcode methods with all fields

@Entity // Marks this class as an entity so JPA creates a table in the database of it.
public class AppUser {

    @Id // Marks this field as the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Makes the database generate a value for this variable, IDENTITY is auto increment.
    private int id;
    @Setter private String username; // Lombok annotation for auto generate a setter, we could put it outside the class to generate a setter for all fields
    @Setter private String password;
    @Setter private LocalDate regDate;
    // Without adding anything in the other class, this becomes a uni-directional relationship.
    @OneToOne() // Sets upp a one-to-one relationship to another class, the class of the variable this annotation is over.
    @JoinColumn(name = "details_id") // "name" is the name of the new column this annotation creates by connecting the two classes.
    @Setter private Details userDetails;

    public AppUser(String username, String password, LocalDate regDate, Details userDetails) {
        this.username = username;
        this.password = password;
        this.regDate = regDate;
        this.userDetails = userDetails;
    }
    public AppUser(String username, String password, LocalDate regDate ) {
        this.username = username;
        this.password = password;
        this.regDate = regDate;
    }
}
