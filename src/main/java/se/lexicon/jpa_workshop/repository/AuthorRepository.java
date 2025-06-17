package se.lexicon.jpa_workshop.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import se.lexicon.jpa_workshop.entity.Author;

import java.util.Set;

public interface AuthorRepository extends CrudRepository<Author, Long> {
    Set<Author> findByFirstName(String firstName);
    Set<Author> findByLastName(String lastName);
    Set<Author> findByFirstNameOrLastNameContainsKeyword(String keyword);
    Set<Author> findByWrittenBooksId(int writtenBooksId);
    void deleteById(int id);

    @Modifying
    @Query("update Author a set a.firstName = :firstName, a.lastName = :lastName where a.id = :id")
    int updateAuthorNameById(@Param("firstName") String firstName, @Param("lastName") String lastName, @Param("id") long id);
}
