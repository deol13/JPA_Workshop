package se.lexicon.jpa_workshop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import se.lexicon.jpa_workshop.entity.*;
import se.lexicon.jpa_workshop.repository.*;

import java.time.LocalDate;

@Component
public class MyCommandLineRunner implements CommandLineRunner {
    private AppUserRepository appUserRepository;
    private DetailsRepository detailsRepository;
    private BookRepository bookRepository;
    private BookLoanRepository bookLoanRepository;
    private AuthorRepository authorRepository;

    @Autowired
    public MyCommandLineRunner(AppUserRepository appUserRepository, DetailsRepository detailsRepository,
                               BookRepository bookRepository, BookLoanRepository bookLoanRepository,
                               AuthorRepository authorRepository) {
        this.appUserRepository = appUserRepository;
        this.detailsRepository = detailsRepository;
        this.bookRepository = bookRepository;
        this.bookLoanRepository = bookLoanRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("###### Application started successfully ######");

        Details details = new Details("John Doe", "JohnDoe@email.com", LocalDate.of(1990, 1, 1));
//        Details createdDetails = detailsRepository.save(details);
//        System.out.printf("Details created: %s\n", createdDetails);

        AppUser user = new AppUser("admin", "123", details);
        AppUser createdAppUser = appUserRepository.save(user);
        AppUser test = appUserRepository.findById(createdAppUser.getId()).orElse(null);
        System.out.printf("AppUser created: %s\n", test);

        Book book = new Book("The Hobbit", 30);
        Book createdBook = bookRepository.save(book);
        System.out.printf("\nBook created: %s\n", createdBook);

//        BookLoan bookLoan = new BookLoan(LocalDate.of(2025, 7, 1), createdAppUser, createdBook);
//        BookLoan createdBookLoan = bookLoanRepository.save(bookLoan);
//        System.out.printf("\nBook loan created: %s\n", createdBookLoan);

//        int id = bookLoanRepository.updateBookLoanReturnedTrueById(createdBookLoan.getId());
//        BookLoan updatedBookLoan = bookLoanRepository.findById((long) id).orElse(null);
//        System.out.printf("\nBook loan updated: %s\n", updatedBookLoan);

        Author author = new Author("John", "Doe", LocalDate.of(1995, 1, 1));
        author.addWrittenBook(book);
        Author createdAuthor = authorRepository.save(author);
        System.out.printf("\nAuthor created: %s\n", createdAuthor);

        int id2 = authorRepository.updateAuthorNameById("Dennis", "Olsen", createdAuthor.getId());
        Author updatedAuthor = authorRepository.findById((long) id2).orElse(null);
        System.out.printf("\nAuthor updated: %s\n", updatedAuthor);
    }
}
