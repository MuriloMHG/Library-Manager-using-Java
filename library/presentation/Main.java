package library.presentation;

import library.application.LoanService;
import library.domain.Book;
import library.domain.Loan;
import library.domain.User;
import library.domain.UserSituation;
import library.infrastructure.BookRepository;
import library.infrastructure.LoanRepository;
import library.infrastructure.UserRepository;

public class Main {
    public static void main(String[] args) {
        BookRepository bookRepository = new BookRepository();
        UserRepository userRepository = new UserRepository();
        LoanRepository loanRepository = new LoanRepository();

        LoanService loanService = new LoanService(
                loanRepository,
                bookRepository,
                userRepository
        );

        System.out.println("=== LIBRARY MANAGEMENT SYSTEM ===");

        Book book = new Book(
                1,
                "Clean Code",
                "Robert C. Martin",
                "9780132350884",
                2
        );

        User user = new User(
                1L,
                "Murilo Henrique",
                "murilo@email.com",
                UserSituation.ACTIVE
        );

        bookRepository.save(book);
        userRepository.save(user);

        System.out.println("\nBook registered:");
        System.out.println(book);

        System.out.println("\nUser registered:");
        System.out.println(user);

        Loan loan = loanService.createLoan(
                user.getId(),
                (long) book.getId()
        );

        System.out.println("\nLoan created:");
        System.out.println(loan);

        System.out.println("\nAvailable quantity after loan:");
        System.out.println(book.getTotalAvailable());

        System.out.println("\nActive loans:");
        loanService.listActiveLoans()
                .forEach(System.out::println);

        loanService.registerReturn(loan.getId());

        System.out.println("\nReturn registered.");

        System.out.println("\nLoan after return:");
        System.out.println(loan);

        System.out.println("\nAvailable quantity after return:");
        System.out.println(book.getTotalAvailable());

        System.out.println("\nActive loans after return:");
        loanService.listActiveLoans()
                .forEach(System.out::println);
    }
}
