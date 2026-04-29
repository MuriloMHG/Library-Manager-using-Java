package library.infrastructure.adapter;

import library.domain.Book;
import library.domain.porta.exit.BookRepositoryPort;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class BookRepositoryInMemory implements BookRepositoryPort {

    private final HashMap<Long, Book> books = new HashMap<>();

    @Override
    public void save(Book book) {
        books.put(book.getId(), book);
    }

    @Override
    public Optional<Book> findById(long id) {
        return Optional.ofNullable(books.get(id));
    }

    @Override
    public List<Book> findAll() {
        return new ArrayList<>(books.values());
    }

    @Override
    public void remove(long id) {
        books.remove(id);
    }
}
