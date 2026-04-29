package library.infrastructure;

import library.domain.Loan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class LoanRepository {
    private final HashMap<Long, Loan> loans = new HashMap<>();

    public void save(Loan loan) {loans.put(loan.getId(), loan);}

    public Optional<Loan> searchWithId(Long id) {return Optional.ofNullable(loans.get(id));}

    public List<Loan> listarTodos() { return new ArrayList<>(loans.values()); }

    public void removeLoan(Long id) { loans.remove(id); }
}
