package library.application;

import library.domain.Book;
import library.infrastructure.BookRepository;

import java.util.List;


public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void registerBook(Book book) {
        bookRepository.save(book);
    }

    public Book SearchWithId(Long id) {
        return bookRepository.searchWithId(id)
                .orElseThrow(() -> new IllegalArgumentException("Book not found."));
    }

    public List<Book> listarTodos() {
        return bookRepository.listarTodos();
    }

    public void remove(Long id) {
        bookRepository.remove(id);
    }
}
