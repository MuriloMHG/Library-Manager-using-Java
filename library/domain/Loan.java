package library.domain;

import java.time.LocalDate;

public class Loan {
    private long id;
    private Book book;
    private User user;
    private LocalDate withdrawDate;
    private LocalDate depositDate;
    private LoanSituation situation;

    public Loan() {}

    public Loan(long id, Book book, User user, LocalDate withdrawDate, LocalDate depositDate) {
        this.id = id;
        this.book = book;
        this.user = user;
        this.withdrawDate = withdrawDate;
        this.depositDate = depositDate;
        this.situation = LoanSituation.ACTIVE;
    }

    public void registerReturn() {
        if (situation == LoanSituation.RETURNED) {
            throw new IllegalStateException("This loan has already been returned.");
        }

        book.returnBook();
        situation = LoanSituation.RETURNED;
    }

    public void registrarDevolucao() {
        registerReturn();
    }

    public long getId() { return id; }

    public Book getBook() { return book; }

    public User getUser() { return user; }

    public LocalDate getWithdrawDate() { return withdrawDate; }

    public LocalDate getDepositDate() { return depositDate; }

    public LoanSituation getSituation() { return situation; }

    public boolean isActive() {
        return situation == LoanSituation.ACTIVE;
    }

    @Override
    public String toString() {
        return "Loan{" +
                "id=" + id +
                ", book=" + book.getTitle() +
                ", user=" + user.getName() +
                ", withdrawDate=" + withdrawDate +
                ", depositDate=" + depositDate +
                ", situation=" + situation +
                '}';
    }
}
