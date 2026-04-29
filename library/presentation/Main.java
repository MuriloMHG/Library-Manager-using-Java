package library.presentation;

import library.domain.Book;
import library.domain.Loan;
import library.domain.User;
import library.domain.UserSituation;
import library.domain.porta.entry.LoanUseCase;
import library.domain.porta.exit.BookRepositoryPort;
import library.domain.porta.exit.LoanRepositoryPort;
import library.domain.porta.exit.NotificationPort;
import library.domain.porta.exit.UserRepositoryPort;
import library.domain.service.LoanService;
import library.infrastructure.adapter.BookRepositoryCsv;
import library.infrastructure.adapter.BookRepositoryInMemory;
import library.infrastructure.adapter.ConsoleNotification;
import library.infrastructure.adapter.LoanRepositoryInMemory;
import library.infrastructure.adapter.UserRepositoryInMemory;

public class Main {

    public static void main(String[] args) {
        System.out.println("=== STAGE 2: HEXAGONAL ARCHITECTURE ===");

        runWithInMemoryAdapters();

        System.out.println("\n-------------------------------------\n");

        runWithCsvBookAdapter();
    }

    private static void runWithInMemoryAdapters() {
        System.out.println("Running with in-memory adapters...");

        BookRepositoryPort bookRepository = new BookRepositoryInMemory();
        UserRepositoryPort userRepository = new UserRepositoryInMemory();
        LoanRepositoryPort loanRepository = new LoanRepositoryInMemory();
        NotificationPort notification = new ConsoleNotification();

        LoanUseCase loanUseCase = new LoanService(
                loanRepository,
                bookRepository,
                userRepository,
                notification
        );

        Book book = new Book(
                1L,
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

        Loan loan = loanUseCase.createLoan(
                user.getId(),
                book.getId()
        );

        System.out.println("Loan created:");
        System.out.println(loan);

        loanUseCase.registerReturn(loan.getId());

        System.out.println("Return registered:");
        System.out.println(loan);
    }

    private static void runWithCsvBookAdapter() {
        System.out.println("Running with CSV adapter for books...");

        BookRepositoryPort bookRepository = new BookRepositoryCsv("books.csv");
        UserRepositoryPort userRepository = new UserRepositoryInMemory();
        LoanRepositoryPort loanRepository = new LoanRepositoryInMemory();
        NotificationPort notification = new ConsoleNotification();

        LoanUseCase loanUseCase = new LoanService(
                loanRepository,
                bookRepository,
                userRepository,
                notification
        );

        Book book = new Book(
                2L,
                "Domain-Driven Design",
                "Eric Evans",
                "9780321125217",
                1
        );

        User user = new User(
                2L,
                "Ana Souza",
                "ana@email.com",
                UserSituation.ACTIVE
        );

        bookRepository.save(book);
        userRepository.save(user);

        Loan loan = loanUseCase.createLoan(
                user.getId(),
                book.getId()
        );

        System.out.println("Loan created using book stored in CSV:");
        System.out.println(loan);

        System.out.println("Books persisted in CSV:");
        bookRepository.findAll().forEach(System.out::println);
    }
}

