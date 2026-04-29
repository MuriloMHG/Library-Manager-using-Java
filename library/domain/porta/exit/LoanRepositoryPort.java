package library.domain.porta.exit;

import library.domain.Loan;

import java.util.List;
import java.util.Optional;

public interface LoanRepositoryPort {
    void save(Loan loan);
    Optional<Loan> findById(long id);
    List<Loan> findAll();
    void remove(long id);
}
