package library.domain.porta.exit;


import library.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepositoryPort {
    void save(Book book);
    Optional<Book> findById(long id);
    List<Book> findAll();
    void remove(long id);
}
