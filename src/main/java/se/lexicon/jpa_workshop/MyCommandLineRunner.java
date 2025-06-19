package se.lexicon.jpa_workshop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.jpa_workshop.entity.*;
import se.lexicon.jpa_workshop.repository.*;

import java.time.LocalDate;
import java.util.Set;

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

    // Remember to think more of service layering when testing a database code and not just all the test in on method.
    // Testing everything in one method can make the tests screw with each other because transactions doesn't happen which affect other tests.
    // It also doesn't test the repositories in a more real scenario,
    // where each task is confined to its own method to make sure each part works before the text task can use the result.
    // If you use tables with associated with other tables, use @Transactional to make sure if one part goes wrong, the rest doesn't affect the database.

    @Override
    public void run(String... args) throws Exception {
        System.out.println("###### Application started successfully ######");
        AppUser appUser = createAppUser("Admin", "123", "John Doe", "JohnDoe@Email.com");
        Book book = createBook();
        Author author = createAuthor(book);
        BookLoan bookLoan = createBookLoan(appUser, book);

        BookLoan updatedBookLoan = updateBookLoanReturned(book, bookLoan);

        Author updatedAuthor = updateAuthorName(author, "J.R.R", "Tolkien");

        //Doesn't work because updatedAuthor is lazy loaded and doesn't contain books.
       // Author updatedAuthor2 = removeBookFromAuthor(book, updatedAuthor);
        Author updatedAuthor2 = removeBookFromAuthor(book, author);

        // Work because appUser2 got a list of book loans
        AppUser appUser2 = createAppUser("Admin2", "1233", "Johnny Doe", "JohnnyDoe@Email.com");
        BookLoan bookLoan2 = createBookLoanWithoutAppUser(book);
        AppUser updatedAppUser = addBookLoanToAppUser(bookLoan2, appUser2);
        AppUser updatedAppUser2 = removeBookLoanFromAppUser(bookLoan2, updatedAppUser);
    }
    @Transactional
    public AppUser createAppUser(String userName, String password, String detailName, String detailEmail) {
        //implement a method to create app user and details
        AppUser user = new AppUser(userName, password, new Details(detailName, detailEmail, LocalDate.of(1990, 1, 1)));
        AppUser createdAppUser = appUserRepository.save(user);
        System.out.printf("\nAppUser created: %s\n", createdAppUser);
        return createdAppUser;

    }
    @Transactional
    public Book createBook() {
        Book book = new Book("Lord of the Ring", 35);
        Book createdBook = bookRepository.save(book);
        System.out.printf("\nBook created: %s\n", createdBook);
        return createdBook;
    }

    public Author createAuthor(Book book){
        Author author = new Author("JRR", "Tolk", LocalDate.of(1892, 1, 3));
        author.addWrittenBook(book);
        Author createdAuthor = authorRepository.save(author);
        System.out.printf("\nAuthor created: %s\n", createdAuthor);
        return createdAuthor;
    }

    @Transactional
    public BookLoan createBookLoan(AppUser user, Book book) {
        AppUser foundAppUser = appUserRepository.findById(user.getId()).orElseThrow(()-> new RuntimeException("AppUser Not found in createBookLoan."));
        Book foundBook = bookRepository.findById(book.getId()).orElseThrow(()-> new RuntimeException("Book Not found in createBookLoan."));

        BookLoan bookLoan = new BookLoan(LocalDate.now().plusDays(10), foundAppUser,foundBook);
        BookLoan createdBookLoan = bookLoanRepository.save(bookLoan);
        System.out.printf("\nAuthor created: %s\n", createdBookLoan);
        return createdBookLoan;
    }

    @Transactional
    public BookLoan createBookLoanWithoutAppUser(Book book){
        Book foundBook = bookRepository.findById(book.getId()).orElseThrow(()-> new RuntimeException("Book Not found in createBookLoan."));

        BookLoan bookLoan = new BookLoan(LocalDate.now().plusDays(10),foundBook);
        BookLoan createdBookLoan = bookLoanRepository.save(bookLoan);
        System.out.printf("\nBookLoan without AppUser created: %s\n", createdBookLoan);
        return createdBookLoan;
    }

    @Transactional
    public Author updateAuthorName(Author author, String firstName, String lastName) {
        authorRepository.updateAuthorNameById(firstName, lastName, author.getId());
        Author updatedAuthor = authorRepository.findById(author.getId()).orElseThrow(()-> new RuntimeException("Author Not found in updateAuthorName."));
        System.out.printf("\nAuthor name updated: %s\n", updatedAuthor);
        return updatedAuthor;
    }

    @Transactional
    public Author removeBookFromAuthor(Book book, Author author) {
        author.removeWrittenBook(book);
        Author updatedAuthor = authorRepository.save(author);
        System.out.printf("\nBook removed: %s\n", updatedAuthor);
        return updatedAuthor;
    }

    @Transactional
    public BookLoan updateBookLoanReturned(Book book, BookLoan loan) {
        bookLoanRepository.updateBookLoanReturnedTrueById(loan.getId());
        BookLoan updatedBookLoan = bookLoanRepository.findById(loan.getId()).orElseThrow(()-> new RuntimeException("BookLoan Not found in updateBookLoanReturned."));
        System.out.printf("\nBook loan updated to returned: %s\n", updatedBookLoan);
        return updatedBookLoan;
    }

    @Transactional
    public AppUser addBookLoanToAppUser(BookLoan bookLoan, AppUser appUser) {
        appUser.addBookLoan(bookLoan);
        AppUser updatedAppUser = appUserRepository.save(appUser);
        System.out.printf("\nBookLoan added to AppUser: %s\n", updatedAppUser);
        return updatedAppUser;
    }

    @Transactional
    public AppUser removeBookLoanFromAppUser(BookLoan bookLoan, AppUser appUser) {
        appUser.removeBookLoan(bookLoan);
        AppUser updatedAppUser = appUserRepository.save(appUser);
        System.out.printf("\nBookLoan removed from AppUser: %s\n", updatedAppUser);
        return updatedAppUser;
    }
}
