package library.infrastructure.adapter;

import library.domain.User;
import library.domain.porta.exit.UserRepositoryPort;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class UserRepositoryInMemory implements UserRepositoryPort {

    private final HashMap<Long, User> users = new HashMap<>();

    @Override
    public void save(User user) {
        users.put(user.getId(), user);
    }

    @Override
    public Optional<User> findById(long id) {
        return Optional.ofNullable(users.get(id));
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }

    @Override
    public void remove(long id) {
        users.remove(id);
    }
}
