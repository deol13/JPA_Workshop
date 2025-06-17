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
        //appUserAndDetails();
        //bookAndAuthor();
        bookLoan();
    }


    public void appUserAndDetails() {
        System.out.println("###### App user and details ######");
        Details details = new Details("John Doe", "JohnDoe@email.com", LocalDate.of(1990, 1, 1));

        AppUser user = new AppUser("admin", "123", LocalDate.of(1995, 1, 1), details);
        AppUser createdAppUser = appUserRepository.save(user);
        System.out.printf("\nAppUser created: %s\n", createdAppUser);
    }

    public void bookAndAuthor() {
        System.out.println("\n###### Book and Author ######");

        Book book = new Book("Lord of the Ring", 35);
        Book createdBook2 = bookRepository.save(book);
        System.out.printf("\nBook created: %s\n", createdBook2);

        Author author = new Author("Dennis", "Olsen", LocalDate.of(1995, 1, 1));
        author.addWrittenBook(book);
        Author createdAuthor = authorRepository.save(author);
        System.out.printf("\nAuthor created: %s\n", createdAuthor);

        int id2 = authorRepository.updateAuthorNameById("Johan", "Olsen", createdAuthor.getId());
        Author updatedAuthor = authorRepository.findById((long) id2).orElse(null);
        System.out.printf("\nAuthor updated: %s\n", updatedAuthor);

        createdAuthor.removeWrittenBook(book);
        Author updatedAuthor2 = authorRepository.save(updatedAuthor);
        System.out.printf("\nBook removed from Author: %s\n", updatedAuthor2);
    }

    public void bookLoan() {
        System.out.println("\n###### Book loan ######");

        Details details = new Details("Johnny Doe", "JohnnyDoe@email.com", LocalDate.of(1990, 1, 1));

        AppUser user = new AppUser("admin2", "123", LocalDate.of(1995, 1, 1), details);
        AppUser createdAppUser = appUserRepository.save(user);
        System.out.printf("\nAppUser created: %s\n", createdAppUser);


        Book book = new Book("The Hobbit", 30);
        Book createdBook = bookRepository.save(book);
        System.out.printf("\nBook created: %s\n", createdBook);

        BookLoan bookLoan = new BookLoan(LocalDate.of(2025, 7, 1), createdBook);
        //BookLoan bookLoan2 = new BookLoan(LocalDate.of(2025, 7, 1), createdBook, createdAppUser);
        BookLoan createdBookLoan = bookLoanRepository.save(bookLoan);

        createdAppUser.addBookLoan(bookLoan);
        AppUser updatedAppUser = appUserRepository.save(createdAppUser);
        System.out.printf("\nAppUser updated with book loan: %s\n", updatedAppUser);

        System.out.printf("\nBookLoan list from App user: %s\n", createdAppUser.getBookLoanList());

        int id = bookLoanRepository.updateBookLoanReturnedTrueById(createdBookLoan.getId());
        BookLoan updatedBookLoan2 = bookLoanRepository.findById((long) id).orElse(null);
        AppUser updatedAppUser2 = appUserRepository.findById(updatedAppUser.getId()).orElse(null); // todo: gives LazyInitializationException
        System.out.printf("\nBook loan updated to returned: %s\n", updatedBookLoan2);

        // todo: test if remove book loan updates book loan as well or if I have to manually save it
        updatedAppUser2.removeBookLoan(updatedBookLoan2);
        AppUser updatedAppUser3 = appUserRepository.save(updatedAppUser);
        System.out.printf("\nAppUser removed book loan: %s\n", updatedAppUser2);

        //BookLoan createdBookLoan2 = bookLoanRepository.save(bookLoan);
        //System.out.printf("\nBook loan: %s\n", updatedBookLoan2);
    }

}
