package se.lexicon.jpa_workshop.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import se.lexicon.jpa_workshop.entity.AppUser;
import se.lexicon.jpa_workshop.entity.Book;
import se.lexicon.jpa_workshop.entity.BookLoan;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookLoanRepository extends CrudRepository<BookLoan, Long> {
    List<BookLoan> findByBorrowerId(long id);
    List<BookLoan> findByBookId(long id);
    List<BookLoan> findAllByReturnedFalse();
    List<BookLoan> findAllByDueDateBeforeAndReturnedFalse(LocalDate dueDate);
    List<BookLoan> findAllByLoanDateBetween(LocalDate startDate, LocalDate endDate);

    //int updateBookLoan(BookLoan bookLoan);

    // Update returns number of affected rows
    @Modifying(clearAutomatically = true) // EntityManager doesn't flush change automatically by default. You won't get the updated data otherwise.
    @Transactional // needed for updates to work, most likely to ensure if associations exists, this update will not work if associated changed doesn't work.
    @Query("update BookLoan b set b.returned = true where b.id = :id")
    int updateBookLoanReturnedTrueById(@Param("id") long id);
}
