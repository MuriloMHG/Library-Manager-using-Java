package library.infrastructure;

import library.domain.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class UserRepository {
    private final HashMap<Long, User> users = new HashMap<>();

    public void save(User user){users.put(user.getId(),user);}

    public Optional<User> buscarPorId(Long id) {return Optional.ofNullable(users.get(id));}

    public List<User> listarTodos() {return new ArrayList<>(users.values());}

    public void remove(Long id) {users.remove(id);}
}
