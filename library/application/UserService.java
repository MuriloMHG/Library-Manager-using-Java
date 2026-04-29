package library.application;


import library.domain.User;
import library.infrastructure.UserRepository;

import java.util.List;


public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void registerUser(User usuario) {
       userRepository.save(usuario);
    }

    public User searchWithId(Long id) {
        return userRepository.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found."));
    }

    public List<User> listarTodos() {
        return userRepository.listarTodos();
    }

    public void remove(Long id) {
       userRepository.remove(id);
    }
}
