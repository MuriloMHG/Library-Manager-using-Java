package library.domain.porta.exit;

import library.domain.Loan;
import library.domain.User;

public interface NotificationPort {
    void notifyOverdue(User user, Loan loan);
}
