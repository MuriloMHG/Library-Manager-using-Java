package library.application;

import library.domain.Book;
import library.domain.Loan;
import library.domain.LoanSituation;
import library.domain.User;
import library.infrastructure.BookRepository;
import library.infrastructure.LoanRepository;
import library.infrastructure.UserRepository;

import java.time.LocalDate;
import java.util.List;

public class LoanService {
    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    private long nextId = 1L;

    public LoanService(
            LoanRepository loanRepository,
            BookRepository bookRepository,
            UserRepository userRepository
    ) {
        this.loanRepository = loanRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    public Loan createLoan(long userId, long bookId) {
        User user = userRepository.buscarPorId(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found."));

        Book book = bookRepository.searchWithId(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Book not found."));

        if (!user.isActive()) {
            throw new IllegalStateException("Suspended users cannot create loans.");
        }

        book.doLoan();

        Loan loan = new Loan(
                nextId++,
                book,
                user,
                LocalDate.now(),
                LocalDate.now().plusDays(7)
        );

        loanRepository.save(loan);
        bookRepository.save(book);

        return loan;
    }

    public void registerReturn(long loanId) {
        Loan loan = loanRepository.searchWithId(loanId)
                .orElseThrow(() -> new IllegalArgumentException("Loan not found."));

        loan.registerReturn();
        loanRepository.save(loan);
        bookRepository.save(loan.getBook());
    }

    public List<Loan> listActiveLoans() {
        return loanRepository.listarTodos()
                .stream()
                .filter(loan -> loan.getSituation() == LoanSituation.ACTIVE)
                .toList();
    }

    public List<Loan> listOverdueLoans(LocalDate today) {
        return loanRepository.listarTodos()
                .stream()
                .filter(loan -> loan.getSituation() == LoanSituation.ACTIVE)
                .filter(loan -> loan.getDepositDate() != null && today.isAfter(loan.getDepositDate()))
                .toList();
    }

    public Loan realizarEmprestimo(Long usuarioId, Long livroId) {
        return createLoan(usuarioId, livroId);
    }

    public void registrarDevolucao(Long emprestimoId) {
        registerReturn(emprestimoId);
    }

    public List<Loan> listarEmprestimosAtivos() {
        return listActiveLoans();
    }
}
