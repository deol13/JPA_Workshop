package se.lexicon.jpa_workshop.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.lexicon.jpa_workshop.entity.Details;

import java.util.List;

@Repository // Marks this interface as a repository for Spring data JPA and as a component for inversion of control and dependency injection (spring core)
public interface DetailsRepository extends CrudRepository<Details, Integer> {
    // By following Spring data JPA naming convention we can create custom version of CrudRepository's already existing methods.
    List<Details> findByEmail(String email);
    List<Details> findByEmailIgnoreCase(String email);
    List<Details> findByNameContaining(String name);
    List<Details> findByNameIgnoreCase(String name);

}
