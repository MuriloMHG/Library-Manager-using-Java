package library.domain.porta.exit;

import library.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepositoryPort {
    void save(User user);
    Optional<User> findById(long id);
    List<User> findAll();
    void remove(long id);
}
