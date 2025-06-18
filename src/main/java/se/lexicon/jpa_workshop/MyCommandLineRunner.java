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
        bookAndAuthor();
        //bookLoan();
    }


    public void appUserAndDetails() {
        System.out.println("###### App user and details ######");
        Details details = new Details("John Doe", "JohnDoe@email.com", LocalDate.of(1990, 1, 1));

        AppUser user = new AppUser("admin", "123", details);
        AppUser createdAppUser = appUserRepository.save(user);

        System.out.printf("AppUser created: %s\n", createdAppUser);
        System.out.printf("Details created: %s\n", createdAppUser.getUserDetails());
    }

    public void bookAndAuthor() {
        System.out.println("\n###### Book and Author ######");

        Book book = new Book("Lord of the Ring", 35);
        //Book createdBook = bookRepository.save(book);
        //System.out.printf("\nBook created: %s\n", createdBook);

        Author author = new Author("JRR", "Tolk", LocalDate.of(1892, 1, 3));
        author.addWrittenBook(book);
        Author createdAuthor = authorRepository.save(author);
        System.out.printf("\nAuthor created: %s\n", createdAuthor);

        Book createdBook = createdAuthor.getWrittenBooks().stream().findFirst().get();
        System.out.printf("\nBook created: %s\n", createdBook);

        int id2 = authorRepository.updateAuthorNameById("J.R.R", "Tolkien", createdAuthor.getId());
        Author updatedAuthor = authorRepository.findById((long) id2).orElse(null);
        if(updatedAuthor != null) {
            System.out.printf("\nAuthor updated: %s\n", updatedAuthor);

            updatedAuthor.removeWrittenBook(createdBook);
            updatedAuthor = authorRepository.save(updatedAuthor);


//            Book updatedBook = bookRepository.findById((long) createdBook.getId()).orElse(null);
//            if(updatedBook.getAuthors() != null)
//            {
//                System.out.printf("\nAuthor in Book checked: %s\n", updatedBook.getAuthors());
//
//                System.out.println("Book removed from Author" + updatedAuthor.getWrittenBooks());
//                System.out.println("Author removed from Book" + book.getAuthors());
//
//                Author updatedAuthor2 = authorRepository.save(updatedAuthor);
//                Book updatedBook2 = bookRepository.save(updatedBook);
//
//                System.out.println("Books in Author from DB after removal " + updatedAuthor2.getWrittenBooks());
//                System.out.println("Authors in Book from DB after removal" + updatedBook2.getAuthors());
//
//
//            }
//            else System.out.println("Author does not exist");
        }
        else System.out.println("Author does not exist");
    }

    public void bookLoan() {
        System.out.println("\n###### Book loan ######");

        Details details = new Details("Johnny Doe", "JohnnyDoe@email.com", LocalDate.of(1990, 1, 1));

        AppUser user = new AppUser("admin2", "123", details);
        AppUser createdAppUser = appUserRepository.save(user);
        System.out.printf("\nAppUser created: %s\n", createdAppUser);

        Book book = new Book("The Hobbit", 30);
        Book createdBook = bookRepository.save(book);
        System.out.printf("\nBook created: %s\n", createdBook);

        BookLoan bookLoan = new BookLoan(LocalDate.of(2025, 7, 1), createdBook);
        BookLoan createdBookLoan = bookLoanRepository.save(bookLoan);

        createdAppUser.addBookLoan(bookLoan);
        AppUser updatedAppUser = appUserRepository.save(createdAppUser);
        System.out.printf("\nAppUser updated with book loan: %s\n", updatedAppUser);

        System.out.printf("\nBookLoan list from App user: %s\n", createdAppUser.getBookLoanList());

        int id = bookLoanRepository.updateBookLoanReturnedTrueById(createdBookLoan.getId());
        BookLoan updatedBookLoan2 = bookLoanRepository.findById((long) id).orElse(null);
        System.out.printf("\nBook loan updated to returned: %s\n", updatedBookLoan2);

        updatedAppUser.removeBookLoan(updatedBookLoan2);
        AppUser updatedAppUser2 = appUserRepository.save(updatedAppUser);
        System.out.printf("\nAppUser removed book loan: %s\n", updatedAppUser2);
        System.out.println("\nBurrower in Book loan: " + updatedBookLoan2.getBorrower() );
    }

}
