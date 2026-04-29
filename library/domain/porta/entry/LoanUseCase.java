package library.domain.porta.entry;

import library.domain.Loan;

import java.util.List;

public interface LoanUseCase {
    Loan createLoan(long userId, long bookId);
    void registerReturn(long loanId);
    List<Loan> listActiveLoans();
    List<Loan> checkOverdues();
}
