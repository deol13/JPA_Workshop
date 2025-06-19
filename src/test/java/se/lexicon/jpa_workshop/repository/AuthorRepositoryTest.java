package se.lexicon.jpa_workshop.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import se.lexicon.jpa_workshop.entity.Author;
import se.lexicon.jpa_workshop.entity.Book;

import java.time.LocalDate;
import java.util.Set;

@DataJpaTest
public class AuthorRepositoryTest {
    @Autowired
    AuthorRepository authorRepo;
    @Autowired
    BookRepository bookRepo;

    @Test
    public void testFindByFirstName() {
        // given
        Author author = new Author("John", "Doe", LocalDate.of(1995, 1, 1));
        Author savedAuthor = authorRepo.save(author);

        // when
        Set<Author> foundAuthor = authorRepo.findByFirstName(savedAuthor.getFirstName());

        // then
        Assertions.assertEquals(1, foundAuthor.size());
        Assertions.assertEquals(savedAuthor, foundAuthor.iterator().next());
    }

    @Test
    public void testFindByLastName() {
        // given
        Author author = new Author("John", "Doe", LocalDate.of(1995, 1, 1));
        Author savedAuthor = authorRepo.save(author);

        // when
        Set<Author> foundAuthor = authorRepo.findByLastName(savedAuthor.getLastName());

        // then
        Assertions.assertEquals(1, foundAuthor.size());
        Assertions.assertEquals(savedAuthor, foundAuthor.iterator().next());
    }

    @Test
    public void testFindByWrittenBooksId() {
        // given
        Book book = new Book("The Hobbit", 30);
        Book savedBook = bookRepo.save(book);
        Author author = new Author("John", "Doe", LocalDate.of(1995, 1, 1));
        author.addWrittenBook(book);
        Author savedAuthor = authorRepo.save(author);

        // when
        Set<Author> foundAuthor = authorRepo.findByWrittenBooksId((int) savedBook.getId());

        // then
        Assertions.assertEquals(1, foundAuthor.size());
        Assertions.assertEquals(savedAuthor, foundAuthor.iterator().next());
    }

    @Test
    public void testFindByFirstNameOrLastNameContainingKeyword() {
        // given
        Author author = new Author("John", "Doe", LocalDate.of(1995, 1, 1));
        Author savedAuthor = authorRepo.save(author);

        // when
        Set<Author> foundAuthor = authorRepo.findByFirstNameOrLastNameContainingKeyword("oh");

        // then
        Assertions.assertEquals(1, foundAuthor.size());
        Assertions.assertEquals(savedAuthor, foundAuthor.iterator().next());
    }

    @Test
    public void testDeleteById() {
        // given
        Author author = new Author("John", "Doe", LocalDate.of(1995, 1, 1));
        Author savedAuthor = authorRepo.save(author);

        // when
        authorRepo.deleteById(savedAuthor.getId());
        Set<Author> foundAuthor = authorRepo.findByFirstName("John");

        // then
        Assertions.assertEquals(0, foundAuthor.size());
    }

    @Test
    public void testUpdateAuthorNameById() {
        // given
        Author author = new Author("John", "Doe", LocalDate.of(1995, 1, 1));
        Author savedAuthor = authorRepo.save(author);

        String expectedFirstName = "Dennis";
        String expectedLastName = "Olsen";

        // when
        authorRepo.updateAuthorNameById(expectedFirstName, expectedLastName, savedAuthor.getId());

        // then
        Author updatedAuthor = authorRepo.findById(savedAuthor.getId()).get();
        Assertions.assertNotNull(updatedAuthor);
        Assertions.assertEquals(expectedFirstName, updatedAuthor.getFirstName());
        Assertions.assertEquals(expectedLastName, updatedAuthor.getLastName());
    }

}
