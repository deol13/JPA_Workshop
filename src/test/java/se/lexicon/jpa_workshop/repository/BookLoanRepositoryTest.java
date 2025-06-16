package se.lexicon.jpa_workshop.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class BookLoanRepositoryTest {
    @Autowired
    AppUserRepository appUserRepository;
    @Autowired
    BookLoanRepository bookLoanRepository;
    @Autowired
    BookRepository bookRepository;

    // todo: implement tests
}
