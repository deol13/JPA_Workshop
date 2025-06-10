package se.lexicon.jpa_workshop.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.lexicon.jpa_workshop.entity.Details;

import java.util.List;

// Marks this interface as a repository for Spring data JPA and as a component for inversion of control and dependency injection (spring core)
@Repository
// By extending from a Spring data JPA repository interface we get a lot of premade methods,
// and we can easily create custom version of those or use @Query to make more complex methods directly in the interface.
// We don't need to implement these methods in a class, Spring data JPA will do it all for us.
// We just need to use this interface as an object.
public interface DetailsRepository extends CrudRepository<Details, Integer> {
    // By following Spring data JPA naming convention we can create custom version of CrudRepository's already existing methods.
    List<Details> findByEmail(String email);
    List<Details> findByEmailIgnoreCase(String email);
    List<Details> findByNameContaining(String name);
    List<Details> findByNameIgnoreCase(String name);

    // spring data jpa generates all basic CRUD operations for the Student entity
    /*
    save(S entity); // insert into student values()
    saveAll(Iterable<S> entities);
    findById(ID id); // select * from student where id = ?
    existsById(ID id);
    findAll();
    findAllById(Iterable<ID> ids);
    long count();
    deleteById(ID id);
    delete(T entity);
    deleteAllById(Iterable<? extends ID> ids);
    deleteAll(Iterable<? extends T> entities);
    deleteAll();
    */
}
