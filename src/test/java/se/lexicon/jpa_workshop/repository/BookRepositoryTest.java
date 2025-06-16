package se.lexicon.jpa_workshop.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import se.lexicon.jpa_workshop.entity.Book;

import java.util.List;

@DataJpaTest
public class BookRepositoryTest {
    @Autowired
    BookRepository bookRepository;

    @Test
    public void testFindByIsbnIgnoreCase_success() {
        // given
        Book book = new Book("The Hobbit", 30);
        bookRepository.save(book);

        // when
        Book foundBook = bookRepository.findByIsbnIgnoreCase(book.getIsbn());

        // then
        Assertions.assertNotNull(foundBook);
        Assertions.assertEquals(book, foundBook);
    }

    @Test
    public void testFindByIsbnIgnoreCase_onlyCapitalLetters_success() {
        // given
        Book book = new Book("The Hobbit", 30);
        bookRepository.save(book);

        // when
        Book foundBook = bookRepository.findByIsbnIgnoreCase(book.getIsbn().toUpperCase());

        // then
        Assertions.assertNotNull(foundBook);
        Assertions.assertEquals(book, foundBook);
    }

    @Test
    public void testFindByIsbnIgnoreCase_failure() {
        // given
        Book book = new Book("The Hobbit", 30);
        bookRepository.save(book);

        // when
        Book foundBook = bookRepository.findByIsbnIgnoreCase("lasdg");

        // then
        Assertions.assertNull(foundBook);
    }

    @Test
    public void testFindByTitleContaining_success() {
        // given
        Book book = new Book("The Hobbit", 30);
        bookRepository.save(book);

        // when
        Book foundBook = bookRepository.findByTitleContaining("Hobbit");

        // then
        Assertions.assertNotNull(foundBook);
        Assertions.assertEquals(book, foundBook);
    }

    @Test
    public void testFindByTitleContaining_failure() {
        // given
        Book book = new Book("The Hobbit", 30);
        bookRepository.save(book);

        // when
        Book foundBook = bookRepository.findByTitleContaining("Eragon");

        // then
        Assertions.assertNull(foundBook);
    }

    @Test
    public void testFindByMaxLoadDaysLessThan_success() {
        // given
        Book book = new Book("The Hobbit", 30);
        Book book2 = new Book("Lord of the Rings", 60);
        bookRepository.save(book);
        bookRepository.save(book2);

        // when
        List<Book> foundBooks = bookRepository.findByMaxLoadDaysLessThan(40);

        // then
        Assertions.assertEquals(1, foundBooks.size());
        Assertions.assertEquals(book, foundBooks.get(0));
    }
}
