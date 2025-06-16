package se.lexicon.jpa_workshop.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import se.lexicon.jpa_workshop.entity.Details;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@DataJpaTest // Will set up and configure a test database we will use during the tests
/*
configuring H2, an in-memory database
setting Hibernate, Spring Data, and the DataSource
performing an @EntityScan
turning on SQL logging
 */
public class DetailsRepositoryTest {

    @Autowired // Because this is  in test, we can Dependency Inject our repository interface directly to a field
    DetailsRepository detailsRepository;

    @Test
    void whenSaveDetail_thenCorrect() {
        // given
        Details details = new Details("testName", "testEmail", LocalDate.now());

        // when
        Details createdDetails = detailsRepository.save(details);

        // then
        Assertions.assertNotNull(createdDetails);
        Assertions.assertEquals(createdDetails.getId(), details.getId());
    }

    @Test
    void whenDeleteDetail_thenCorrect() {
        // given
        Details details = new Details("testName", "testEmail", LocalDate.now());

        // when
        detailsRepository.delete(details);
        Details deletedDetails = detailsRepository.findById(details.getId()).orElse(null);

        // then
        Assertions.assertNull(deletedDetails);
    }

    @Test
    void whenUpdateDetail_thenCorrect() {
        // given
        Details details = new Details("testName", "testEmail", LocalDate.now());
        Details createdDetails = detailsRepository.save(details);

        // when
        details.setName("updateName");
        Details updatedDetails = detailsRepository.save(createdDetails);

        // then
        Assertions.assertNotNull(updatedDetails);
        Assertions.assertEquals(updatedDetails.getId(), createdDetails.getId());
        Assertions.assertEquals("updateName", updatedDetails.getName());
    }

    @Test
    void whenFindDetailsById_thenCorrect() {
        //given
        Details details = new Details("testName", "testEmail", LocalDate.now());
        Details createdDetails = detailsRepository.save(details);

        // when
        Optional<Details> foundDetails = detailsRepository.findById(createdDetails.getId());

        // then
        Assertions.assertTrue(foundDetails.isPresent());
        Assertions.assertEquals(createdDetails.getId(), foundDetails.get().getId());
    }


    @Test
    void whenFindByEmail_match_thenCorrect(){
        // given
        Details details = new Details("testName", "testEmail", LocalDate.now());
        Details createdDetails = detailsRepository.save(details);

        // when
        List<Details> foundDetails = detailsRepository.findByEmail(createdDetails.getEmail());

        // then
        Assertions.assertFalse(foundDetails.isEmpty());
        Assertions.assertEquals(1, foundDetails.size());
        Assertions.assertEquals(foundDetails.get(0), createdDetails);
    }

    @Test
    void whenFindByEmail_noMatch_thenCorrect(){
        // given
        Details details = new Details("testName", "testEmail", LocalDate.now());
        Details createdDetails = detailsRepository.save(details);

        // when
        List<Details> foundDetails = detailsRepository.findByEmail("matt@email.com");

        // then
        Assertions.assertTrue(foundDetails.isEmpty());
    }

    @Test
    void whenFindByEmailIgnoreCase_match_thenCorrect(){
        // given
        Details details = new Details("testName", "testEmail", LocalDate.now());
        Details createdDetails = detailsRepository.save(details);

        // when
        List<Details> foundDetails = detailsRepository.findByEmailIgnoreCase(createdDetails.getEmail().toLowerCase());

        // then
        Assertions.assertFalse(foundDetails.isEmpty());
        Assertions.assertEquals(1, foundDetails.size());
        Assertions.assertEquals(foundDetails.get(0), createdDetails);
    }

    @Test
    void whenFindByEmailIgnoreCase_noMatch_thenCorrect(){
        // given
        Details details = new Details("testName", "testEmail", LocalDate.now());
        Details createdDetails = detailsRepository.save(details);

        // when
        List<Details> foundDetails = detailsRepository.findByEmailIgnoreCase("matt@email.com");

        // then
        Assertions.assertTrue(foundDetails.isEmpty());
    }

    @Test
    void whenFindByNameContaining_match_thenCorrect(){
        // given
        Details details = new Details("testName", "testEmail", LocalDate.now());
        Details createdDetails = detailsRepository.save(details);

        // when
        List<Details> foundDetails = detailsRepository.findByNameContaining("Name");

        // then
        Assertions.assertFalse(foundDetails.isEmpty());
        Assertions.assertEquals(1, foundDetails.size());
        Assertions.assertEquals(foundDetails.get(0), createdDetails);
    }

    @Test
    void whenFindByNameContaining_noMatch_thenCorrect(){
        // given
        Details details = new Details("testName", "testEmail", LocalDate.now());
        Details createdDetails = detailsRepository.save(details);

        // when
        List<Details> foundDetails = detailsRepository.findByNameContaining("Abra");

        // then
        Assertions.assertTrue(foundDetails.isEmpty());
    }

    @Test
    void whenFindByNameIgnoreCase_matches_thenCorrect(){
        // given
        Details details = new Details("testName", "testEmail", LocalDate.now());
        Details createdDetails = detailsRepository.save(details);

        // when
        List<Details> foundDetails = detailsRepository.findByNameIgnoreCase(createdDetails.getName().toLowerCase());

        // then
        Assertions.assertFalse(foundDetails.isEmpty());
        Assertions.assertEquals(1, foundDetails.size());
        Assertions.assertEquals(foundDetails.get(0), createdDetails);
    }

    @Test
    void whenFindByNameIgnoreCase_noMatches_thenCorrect(){
        // given
        Details details = new Details("testName", "testEmail", LocalDate.now());
        Details createdDetails = detailsRepository.save(details);

        // when
        List<Details> foundDetails = detailsRepository.findByNameIgnoreCase("Abra");

        // then
        Assertions.assertTrue(foundDetails.isEmpty());
    }
}
