package se.lexicon.jpa_workshop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import se.lexicon.jpa_workshop.entity.AppUser;
import se.lexicon.jpa_workshop.entity.Details;
import se.lexicon.jpa_workshop.repository.AppUserRepository;
import se.lexicon.jpa_workshop.repository.DetailsRepository;

import java.time.LocalDate;

@Component
public class MyCommandLineRunner implements CommandLineRunner {
    private AppUserRepository appUserRepository;
    private DetailsRepository detailsRepository;
    //private BookRepository bookRepository;
    //private BookLoanRepository bookLoanRepository;

    @Autowired
    public MyCommandLineRunner(AppUserRepository appUserRepository, DetailsRepository detailsRepository) {//, BookRepository bookRepository, BookLoanRepository bookLoanRepository) {
        this.appUserRepository = appUserRepository;
        this.detailsRepository = detailsRepository;
        //this.bookRepository = bookRepository;
        //this.bookLoanRepository = bookLoanRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("###### Application started successfully ######");

        Details details = new Details("John Doe", "JohnDoe@email.com", LocalDate.of(1990, 1, 1));
        Details createdDetails = detailsRepository.save(details);
        System.out.printf("Details created: %s\n", createdDetails);

        AppUser user = new AppUser("admin", "123", LocalDate.of(1995, 1, 1), createdDetails);
        AppUser createdAppUser = appUserRepository.save(user);
        System.out.printf("AppUser created: %s\n", createdAppUser);

    }
}
