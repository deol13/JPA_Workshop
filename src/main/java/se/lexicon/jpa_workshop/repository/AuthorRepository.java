package se.lexicon.jpa_workshop.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import se.lexicon.jpa_workshop.entity.Author;

import java.util.Set;

public interface AuthorRepository extends CrudRepository<Author, Long> {
    Set<Author> findByFirstName(String firstName);
    Set<Author> findByLastName(String lastName);
    Set<Author> findByWrittenBooksId(int writtenBooksId);
    void deleteById(int id);

    //Set<Author> findByFirstNameOrLastNameContaining(String firstName, String lastName);  // ??
    @Query("select a from Author a where a.firstName LIKE %:keyword% OR a.lastName LIKE %:keyword%")
    Set<Author> findByFirstNameOrLastNameContainingKeyword(String keyword);

    // Update returns number of affected rows
    @Modifying(clearAutomatically = true) // EntityManager doesn't flush change automatically by default. You won't get the updated data otherwise.
    @Transactional // needed for updates to work, most likely to ensure if associations exists, this update will not work if associated changed doesn't work.
    @Query("update Author a set a.firstName = :firstName, a.lastName = :lastName where a.id = :id")
    int updateAuthorNameById(@Param("firstName") String firstName, @Param("lastName") String lastName, @Param("id") long id);
}
