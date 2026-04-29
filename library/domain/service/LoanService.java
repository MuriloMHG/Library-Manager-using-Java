package library.domain.service;

import library.domain.Book;
import library.domain.Loan;
import library.domain.LoanSituation;
import library.domain.User;
import library.domain.porta.entry.LoanUseCase;
import library.domain.porta.exit.BookRepositoryPort;
import library.domain.porta.exit.LoanRepositoryPort;
import library.domain.porta.exit.NotificationPort;
import library.domain.porta.exit.UserRepositoryPort;

import java.time.LocalDate;
import java.util.List;

public class LoanService implements LoanUseCase {

    private final LoanRepositoryPort loanRepository;
    private final BookRepositoryPort bookRepository;
    private final UserRepositoryPort userRepository;
    private final NotificationPort notification;

    private long nextId = 1L;

    public LoanService(
            LoanRepositoryPort loanRepository,
            BookRepositoryPort bookRepository,
            UserRepositoryPort userRepository,
            NotificationPort notification
    ) {
        this.loanRepository = loanRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.notification = notification;
    }

    @Override
    public Loan createLoan(long userId, long bookId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found."));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Book not found."));

        if (!user.isActive()) {
            throw new IllegalStateException("Suspended users cannot create loans.");
        }

        book.doLoan();

        long id = generateNextId();

        Loan loan = new Loan(
                id,
                book,
                user,
                LocalDate.now(),
                LocalDate.now().plusDays(7)
        );

        loanRepository.save(loan);
        bookRepository.save(book);

        return loan;
    }

    @Override
    public void registerReturn(long loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new IllegalArgumentException("Loan not found."));

        loan.registerReturn();

        loanRepository.save(loan);
        bookRepository.save(loan.getBook());
    }

    @Override
    public List<Loan> listActiveLoans() {
        return loanRepository.findAll()
                .stream()
                .filter(Loan::isActive)
                .toList();
    }

    @Override
    public List<Loan> checkOverdues() {
        LocalDate today = LocalDate.now();

        List<Loan> overdueLoans = loanRepository.findAll()
                .stream()
                .filter(loan -> loan.getSituation() == LoanSituation.ACTIVE)
                .filter(loan -> loan.getDepositDate() != null && today.isAfter(loan.getDepositDate()))
                .toList();

        overdueLoans.forEach(loan -> {
            loan.markOverdue();
            loanRepository.save(loan);
            notification.notifyOverdue(loan.getUser(), loan);
        });

        return overdueLoans;
    }

    private long generateNextId() {
        long maxId = loanRepository.findAll()
                .stream()
                .mapToLong(Loan::getId)
                .max()
                .orElse(0L);

        nextId = maxId + 1;
        return nextId;
    }
}
