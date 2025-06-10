package se.lexicon.jpa_workshop.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDate;

// Lombok annotations, you can exclude fields. (exclude =)
@NoArgsConstructor // Creates a default constructor
@AllArgsConstructor // Creates a constructor with all fields
@Getter // Creates getter methods for all fields
@ToString // Creates a toString with all fields
@EqualsAndHashCode // Creates Equal and Hashcode methods with all fields

@Entity // Marks this class as an entity so JPA creates a table in the database of it.
public class Details {
    @Id // Marks this field as the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Makes the database generate a value for this variable, IDENTITY is auto increment.
    private int id;
    @Setter private String name; // Lombok annotation for auto generate a setter, we could put it outside the class to generate a setter for all fields
    @Setter private String email;
    @Setter private LocalDate birthDate;

    public Details(String name, String email, LocalDate birthDate) {
        this.name = name;
        this.email = email;
        this.birthDate = birthDate;
    }
}
