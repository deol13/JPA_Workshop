package se.lexicon.jpa_workshop.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import se.lexicon.jpa_workshop.entity.AppUser;
import se.lexicon.jpa_workshop.entity.Book;
import se.lexicon.jpa_workshop.entity.BookLoan;

import java.time.LocalDate;
import java.util.List;

@DataJpaTest
public class BookLoanRepositoryTest {
    @Autowired
    AppUserRepository appUserRepository;
    @Autowired
    BookLoanRepository bookLoanRepository;
    @Autowired
    BookRepository bookRepository;

    @Test
    public void testFindByBorrowerId() {
        // given
        AppUser borrower = new AppUser("John", "Doe");
        appUserRepository.save(borrower);
        Book book = new Book("The Hobbit", 30);
        bookRepository.save(book);

        BookLoan bookLoan = new BookLoan(LocalDate.of(2025,7,15), borrower, book);
        bookLoanRepository.save(bookLoan);

        // when
        List<BookLoan> bookLoans = bookLoanRepository.findByBorrowerId(borrower.getId());

        // then
        Assertions.assertEquals(1, bookLoans.size());
        Assertions.assertEquals(bookLoan, bookLoans.get(0));
        Assertions.assertEquals(book, bookLoans.get(0).getBook());
        Assertions.assertEquals(borrower, bookLoans.get(0).getBorrower());
    }

    @Test
    public void testFindByBookId() {
        // given
        AppUser borrower = new AppUser("John", "Doe");
        appUserRepository.save(borrower);
        Book book = new Book("The Hobbit", 30);
        bookRepository.save(book);

        BookLoan bookLoan = new BookLoan(LocalDate.of(2025,7,15), borrower, book);
        bookLoanRepository.save(bookLoan);

        // when
        List<BookLoan> bookLoans = bookLoanRepository.findByBookId(book.getId());

        // then
        Assertions.assertEquals(1, bookLoans.size());
        Assertions.assertEquals(bookLoan, bookLoans.get(0));
        Assertions.assertEquals(book, bookLoans.get(0).getBook());
        Assertions.assertEquals(borrower, bookLoans.get(0).getBorrower());
    }

    @Test
    public void testFindAllByReturnedFalse() {
        // given
        BookLoan bookLoan = new BookLoan(LocalDate.of(2025,7,15));
        BookLoan bookLoan2 = new BookLoan(LocalDate.of(2025,7,15));
        BookLoan bookLoan3 = new BookLoan(LocalDate.of(2025,7,15));
        bookLoanRepository.save(bookLoan);
        bookLoanRepository.save(bookLoan2);
        bookLoanRepository.save(bookLoan3);

        // Because @PrePersist is used for loanDate and returned this needs to be done after the first save
        bookLoan3.setReturned(true);
        bookLoanRepository.save(bookLoan3); // update

        // when
        List<BookLoan> bookLoans = bookLoanRepository.findAllByReturnedFalse();

        // then
        Assertions.assertEquals(2, bookLoans.size());
        Assertions.assertFalse(bookLoans.get(0).isReturned());
        Assertions.assertFalse(bookLoans.get(1).isReturned());
    }

    @Test
    public void testFindAllByDueDateBeforeAndReturnedFalse() {
        // given
        BookLoan bookLoan = new BookLoan(LocalDate.of(2025,7,15));
        BookLoan bookLoan2 = new BookLoan(LocalDate.of(2025,6,15));
        BookLoan bookLoan3 = new BookLoan(LocalDate.of(2025,5,15));
        bookLoanRepository.save(bookLoan);
        bookLoanRepository.save(bookLoan2);
        bookLoanRepository.save(bookLoan3);

        // Because @PrePersist is used for loanDate and returned this needs to be done after the first save
        bookLoan3.setReturned(true);
        bookLoanRepository.save(bookLoan3); // update

        // when
        List<BookLoan> bookLoans = bookLoanRepository.findAllByDueDateBeforeAndReturnedFalse(LocalDate.now());

        // then
        Assertions.assertEquals(1, bookLoans.size());
        Assertions.assertFalse(bookLoans.get(0).isReturned());
        Assertions.assertTrue(bookLoans.get(0).getDueDate().isBefore(LocalDate.now()));
    }

    @Test
    public void testFindAllByLoanDateBetween() {
        // given
        BookLoan bookLoan = new BookLoan(LocalDate.of(2025,7,10));
        BookLoan bookLoan2 = new BookLoan(LocalDate.of(2025,7,10));
        BookLoan bookLoan3 = new BookLoan(LocalDate.of(2025,7,10));
        bookLoanRepository.save(bookLoan);
        bookLoanRepository.save(bookLoan2);
        bookLoanRepository.save(bookLoan3);

        // Same issue with @PrePersist here
        bookLoan.setLoanDate(LocalDate.of(2025,6,12));
        bookLoan3.setLoanDate(LocalDate.of(2025,6,15));
        bookLoanRepository.save(bookLoan); // update
        bookLoanRepository.save(bookLoan3); // update

        // when
        List<BookLoan> bookLoans = bookLoanRepository.findAllByLoanDateBetween(LocalDate.of(2025,6,9), LocalDate.of(2025,6,16));

        Assertions.assertEquals(2, bookLoans.size());
        Assertions.assertTrue(bookLoans.get(0).getLoanDate().isAfter(LocalDate.of(2025,6,9)));
        Assertions.assertTrue(bookLoans.get(1).getLoanDate().isAfter(LocalDate.of(2025,6,9)));
        Assertions.assertTrue(bookLoans.get(0).getLoanDate().isBefore(LocalDate.of(2025,6,16)));
        Assertions.assertTrue(bookLoans.get(1).getLoanDate().isBefore(LocalDate.of(2025,6,16)));
    }

    @Test
    public void testUpdateBookLoanReturnedTrueById(){
        // given
        BookLoan bookLoan = new BookLoan(LocalDate.of(2025,7,10));
        BookLoan savedBookLoan = bookLoanRepository.save(bookLoan);

        // when
        int id = bookLoanRepository.updateBookLoanReturnedTrueById(savedBookLoan.getId());

        // when
        BookLoan updatedBookLoan = bookLoanRepository.findById((long) id).orElse(null);
        Assertions.assertNotNull(updatedBookLoan);
        Assertions.assertTrue(updatedBookLoan.isReturned());
    }
}
