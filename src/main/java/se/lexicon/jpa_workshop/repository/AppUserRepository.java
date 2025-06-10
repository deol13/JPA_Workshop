package se.lexicon.jpa_workshop.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import se.lexicon.jpa_workshop.entity.AppUser;

import java.time.LocalDate;
import java.util.List;

@Repository // Marks this interface as a repository for Spring data JPA and as a component for inversion of control and dependency injection (spring core)
public interface AppUserRepository extends CrudRepository<AppUser, Integer> {
    // By following Spring data JPA naming convention we can create custom version of CrudRepository's already existing methods.
    List<AppUser> findByUsername(String username);
    List<AppUser> findByRegDateBetween(LocalDate regDate, LocalDate regDate2);
    // Or we can create our own queries if the existing methods are not enough.
    @Query("SELECT user FROM AppUser user WHERE user.userDetails.id = :id")
    AppUser findByDetailsId(@Param("id") int id);
}
