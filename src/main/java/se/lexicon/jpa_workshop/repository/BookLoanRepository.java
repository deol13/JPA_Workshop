package se.lexicon.jpa_workshop.repository;

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
    List<BookLoan> findByBorrowerId(int id);
    List<BookLoan> findByBookId(int id);
    List<BookLoan> findAllByReturnedFalse();
    List<BookLoan> findAllByDueDateBeforeAndReturnedFalse(LocalDate dueDate);
    List<BookLoan> findAllByLoanDateBetween(LocalDate startDate, LocalDate endDate);

    //int updateBookLoan(BookLoan bookLoan);

    @Modifying(clearAutomatically = true)
    @Query("update BookLoan b set b.returned = true where b.id = :id")
    int updateBookLoanReturnedTrueById(@Param("id") int id);


}
