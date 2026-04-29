package library.infrastructure.adapter;

import library.domain.Loan;
import library.domain.User;
import library.domain.porta.exit.NotificationPort;

public class ConsoleNotification implements NotificationPort {

    @Override
    public void notifyOverdue(User user, Loan loan) {
        System.out.println("[NOTIFICATION]");
        System.out.println("User: " + user.getName());
        System.out.println("Loan " + loan.getId() + " is overdue.");
        System.out.println("Book: " + loan.getBook().getTitle());
        System.out.println("Due date: " + loan.getDepositDate());
    }
}
