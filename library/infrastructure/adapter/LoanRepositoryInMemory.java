package library.infrastructure.adapter;

import library.domain.Loan;
import library.domain.porta.exit.LoanRepositoryPort;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class LoanRepositoryInMemory implements LoanRepositoryPort {

    private final HashMap<Long, Loan> loans = new HashMap<>();

    @Override
    public void save(Loan loan) {
        loans.put(loan.getId(), loan);
    }

    @Override
    public Optional<Loan> findById(long id) {
        return Optional.ofNullable(loans.get(id));
    }

    @Override
    public List<Loan> findAll() {
        return new ArrayList<>(loans.values());
    }

    @Override
    public void remove(long id) {
        loans.remove(id);
    }
}
