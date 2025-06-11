package se.lexicon.jpa_workshop;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import se.lexicon.jpa_workshop.entity.AppUser;
import se.lexicon.jpa_workshop.entity.Details;
import se.lexicon.jpa_workshop.repository.AppUserRepository;
import se.lexicon.jpa_workshop.repository.DetailsRepository;

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
public class AppUserRepositoryTest {
    @Autowired // Because this is  in test, we can Dependency Inject our repository interface directly to a field
    AppUserRepository appUserRepository;
    @Autowired
    DetailsRepository detailsRepository;

    @Test
    void whenSaveAppUser_thenCorrect() {
        // given
        AppUser appUser = new AppUser("un","123456", LocalDate.now());

        // when
        AppUser createdUser = appUserRepository.save(appUser);

        // then
        Assertions.assertNotNull(createdUser);
        Assertions.assertEquals(createdUser.getId(), appUser.getId());
    }

    @Test
    void whenDeleteAppUser_thenCorrect() {
        // given
        AppUser appUser = new AppUser("un","123456", LocalDate.now());

        // when
        appUserRepository.delete(appUser);
        AppUser deletedUser = appUserRepository.findById(appUser.getId()).orElse(null);

        // then
        Assertions.assertNull(deletedUser);
    }

    @Test
    void whenUpdateAppUser_thenCorrect() {
        // given
        AppUser appUser = new AppUser("un","123456", LocalDate.now());
        AppUser createdUser = appUserRepository.save(appUser);

        // when
        appUser.setUsername("update");
        AppUser updatedUser = appUserRepository.save(createdUser);

        // then
        Assertions.assertNotNull(updatedUser);
        Assertions.assertEquals(updatedUser.getId(), createdUser.getId());
        Assertions.assertEquals("update", updatedUser.getUsername());
    }

    @Test
    void whenFindAppUsersById_thenCorrect() {
        //given
        AppUser appUser = new AppUser("un","123456", LocalDate.now());
        AppUser createdUser = appUserRepository.save(appUser);

        // when
        Optional<AppUser> foundAppUser = appUserRepository.findById(createdUser.getId());

        // then
        Assertions.assertTrue(foundAppUser.isPresent());
        Assertions.assertEquals(createdUser.getId(), foundAppUser.get().getId());
    }

    @Test
    void whenFindByUsername_match_thenCorrect() {
        // given
        AppUser appUser = new AppUser("un","123456", LocalDate.now());
        AppUser appUser2 = new AppUser("un2","1234567", LocalDate.now());
        AppUser createdUser = appUserRepository.save(appUser);
        appUserRepository.save(appUser2);

        // when
        List<AppUser> foundAppUser = appUserRepository.findByUsername(createdUser.getUsername());

        // then
        Assertions.assertFalse(foundAppUser.isEmpty());
        Assertions.assertEquals(1, foundAppUser.size());
        Assertions.assertEquals(foundAppUser.get(0), createdUser);
    }

    @Test
    void whenFindByUsername_noMatch_thenCorrect() {
        // given
        AppUser appUser = new AppUser("un","123456", LocalDate.now());
        AppUser appUser2 = new AppUser("un2","1234567", LocalDate.now());
        AppUser createdUser = appUserRepository.save(appUser);
        appUserRepository.save(appUser2);

        // when
        List<AppUser> foundAppUser = appUserRepository.findByUsername("Onto");

        // then
        Assertions.assertTrue(foundAppUser.isEmpty());
    }

    @Test
    void whenFindByRegDateBetween_match_thenCorrect() {
        // given
        AppUser appUser = new AppUser("un","123456", LocalDate.of(2020,1,1));
        AppUser appUser2 = new AppUser("un2","1234567", LocalDate.now());
        AppUser createdUser = appUserRepository.save(appUser);
        AppUser createdUser2 = appUserRepository.save(appUser2);

        // when
        List<AppUser> foundAppUser = appUserRepository.findByRegDateBetween(LocalDate.of(2019,1,1), LocalDate.of(2021,1,1));

        // then
        Assertions.assertFalse(foundAppUser.isEmpty());
        Assertions.assertEquals(1, foundAppUser.size());
        Assertions.assertEquals(foundAppUser.get(0), createdUser);
    }

    @Test
    void whenFindByRegDateBetween_noMatch_thenCorrect() {
        // given
        AppUser appUser = new AppUser("un","123456", LocalDate.of(2020,1,1));
        AppUser appUser2 = new AppUser("un2","1234567", LocalDate.now());
        AppUser createdUser = appUserRepository.save(appUser);
        AppUser createdUser2 = appUserRepository.save(appUser2);

        // when
        List<AppUser> foundAppUser = appUserRepository.findByRegDateBetween(LocalDate.of(2019,1,1), LocalDate.of(2019,6,1));

        // then
        Assertions.assertTrue(foundAppUser.isEmpty());
    }

    @Test
    void whenFindByDetailsId_match_thenCorrect() {
        // given
        Details details = new Details("testName","testEmail", LocalDate.now());
        Details createdDetails = detailsRepository.save(details);

        AppUser appUser = new AppUser("un","123456", LocalDate.now(), createdDetails);
        AppUser createdUser = appUserRepository.save(appUser);

        // when
        AppUser foundAppUser = appUserRepository.findByDetailsId(createdUser.getUserDetails().getId());

        // then
        Assertions.assertNotNull(foundAppUser);
        Assertions.assertEquals(createdUser.getUserDetails().getId(), foundAppUser.getUserDetails().getId());
    }

    @Test
    void whenFindByDetailsId_noMatch_thenCorrect() {
        // given
        Details details = new Details("testName","testEmail", LocalDate.now());
        Details createdDetails = detailsRepository.save(details);

        AppUser appUser = new AppUser("un","123456", LocalDate.now(), createdDetails);
        AppUser createdUser = appUserRepository.save(appUser);

        // when
        AppUser foundAppUser = appUserRepository.findByDetailsId(1000);

        // then
        Assertions.assertNull(foundAppUser);
    }
}
