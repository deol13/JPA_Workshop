package se.lexicon.jpa_workshop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import se.lexicon.jpa_workshop.entity.AppUser;
import se.lexicon.jpa_workshop.entity.Book;
import se.lexicon.jpa_workshop.entity.BookLoan;
import se.lexicon.jpa_workshop.entity.Details;
import se.lexicon.jpa_workshop.repository.AppUserRepository;
import se.lexicon.jpa_workshop.repository.BookLoanRepository;
import se.lexicon.jpa_workshop.repository.BookRepository;
import se.lexicon.jpa_workshop.repository.DetailsRepository;

import java.time.LocalDate;

@Component
public class MyCommandLineRunner implements CommandLineRunner {
    private AppUserRepository appUserRepository;
    private DetailsRepository detailsRepository;
    private BookRepository bookRepository;
    private BookLoanRepository bookLoanRepository;

    @Autowired
    public MyCommandLineRunner(AppUserRepository appUserRepository, DetailsRepository detailsRepository, BookRepository bookRepository, BookLoanRepository bookLoanRepository) {
        this.appUserRepository = appUserRepository;
        this.detailsRepository = detailsRepository;
        this.bookRepository = bookRepository;
        this.bookLoanRepository = bookLoanRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("###### Application started successfully ######");

        Details details = new Details("John Doe", "JohnDoe@email.com", LocalDate.of(1990, 1, 1));
        //Details createdDetails = detailsRepository.save(details);
        //System.out.printf("Details created: %s\n", createdDetails);

        AppUser user = new AppUser("admin", "123", LocalDate.of(1995, 1, 1), details);
        AppUser createdAppUser = appUserRepository.save(user);
        System.out.printf("AppUser created: %s\n", createdAppUser);

        Book book = new Book("The Hobbit", 30);
        Book createdBook = bookRepository.save(book);
        System.out.printf("\nBook created: %s\n", createdBook);

        BookLoan bookLoan = new BookLoan(LocalDate.of(2025, 7, 1), createdAppUser, createdBook);
        BookLoan createdBookLoan = bookLoanRepository.save(bookLoan);
        System.out.printf("\nBook loan created: %s\n", createdBookLoan);

//        createdBookLoan.setReturned(true);
//        int id = bookLoanRepository.updateBookLoan(createdBookLoan);
//        BookLoan updatedBookLoan = bookLoanRepository.findById((long) id).orElse(null);
//        System.out.printf("\nBook loan updated: %s\n", updatedBookLoan);

//        int id = bookLoanRepository.updateBookLoanReturnedTrueById(createdBookLoan.getId());
////                createdBookLoan.getLoanDate(), createdBookLoan.getDueDate(),
////                createdBookLoan.getBorrower(), createdBookLoan.getBook());
//        BookLoan updatedBookLoan = bookLoanRepository.findById((long) id).orElse(null);
//        System.out.printf("\nBook loan updated: %s\n", updatedBookLoan);

    }
}
