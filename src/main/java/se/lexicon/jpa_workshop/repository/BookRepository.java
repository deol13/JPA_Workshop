package se.lexicon.jpa_workshop.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import se.lexicon.jpa_workshop.entity.Book;

import java.util.List;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {
    Book findByIsbnIgnoreCase(String ISBN);
    Book findByTitleContaining(String title);
    List<Book> findByMaxLoadDaysLessThan(int maxLoadDays);
}
