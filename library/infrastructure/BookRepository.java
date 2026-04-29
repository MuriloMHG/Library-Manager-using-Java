package library.infrastructure;

import library.domain.Book;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class BookRepository {
    private final HashMap<Long, Book> books = new HashMap<>();

    public void save(Book book)
    {
        books.put((long) book.getId(), book);
    }

    public Optional<Book> searchWithId(Long id) {
        return Optional.ofNullable(books.get(id));
    }

    public List<Book> listarTodos() {
        return new ArrayList<>(books.values());
    }

    public void remove(Long id) {
        books.remove(id);
    }
}
