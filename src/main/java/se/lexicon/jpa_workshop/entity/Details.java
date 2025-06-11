package se.lexicon.jpa_workshop.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDate;

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
public class Details {

    // Marks this field as the primary key in this Entity
    // Makes the database generate a value for this variable,
    // GenerationType is different ways to give this field a value, IDENTITY is auto increment
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // Follow naming conventions

    // @Setter is a Lombok annotation for auto generate a setter method.
    // We could put @Setter outside the class with all the others
    // but if we just want specific setters and getters to be made, we need to put it before those fields.
    // Here we don't want a setter for id.
    @Setter private String name;
    @Setter private String email;
    @Setter private LocalDate birthDate;

    public Details(String name, String email, LocalDate birthDate) {
        this.name = name;
        this.email = email;
        this.birthDate = birthDate;
    }
}
