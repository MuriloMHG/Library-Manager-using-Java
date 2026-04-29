package library.infrastructure.adapter;

import library.domain.Book;
import library.domain.porta.exit.BookRepositoryPort;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookRepositoryCsv implements BookRepositoryPort {

    private final Path filePath;

    public BookRepositoryCsv(String fileName) {
        this.filePath = Path.of(fileName);
        createFileIfMissing();
    }

    @Override
    public void save(Book book) {
        List<Book> books = findAll();

        books.removeIf(b -> b.getId() == book.getId());
        books.add(book);

        writeAll(books);
    }

    @Override
    public Optional<Book> findById(long id) {
        return findAll()
                .stream()
                .filter(book -> book.getId() == id)
                .findFirst();
    }

    @Override
    public List<Book> findAll() {
        try {
            List<String> lines = Files.readAllLines(filePath);
            List<Book> books = new ArrayList<>();

            for (String line : lines) {
                if (line.isBlank()) {
                    continue;
                }

                String[] parts = line.split(";");

                long id = Long.parseLong(parts[0]);
                String title = parts[1];
                String author = parts[2];
                String isbn = parts[3];
                int totalAvailable = Integer.parseInt(parts[4]);

                books.add(new Book(id, title, author, isbn, totalAvailable));
            }

            return books;
        } catch (IOException e) {
            throw new RuntimeException("Failed to read book CSV file.", e);
        }
    }

    @Override
    public void remove(long id) {
        List<Book> books = findAll();

        books.removeIf(book -> book.getId() == id);

        writeAll(books);
    }

    private void writeAll(List<Book> books) {
        List<String> lines = books.stream()
                .map(this::toCsvLine)
                .toList();

        try {
            Files.write(
                    filePath,
                    lines,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING
            );
        } catch (IOException e) {
            throw new RuntimeException("Failed to write book CSV file.", e);
        }
    }

    private String toCsvLine(Book book) {
        return book.getId() + ";" +
                book.getTitle() + ";" +
                book.getAuthor() + ";" +
                book.getIsbn() + ";" +
                book.getTotalAvailable();
    }

    private void createFileIfMissing() {
        try {
            if (!Files.exists(filePath)) {
                Files.createFile(filePath);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to create book CSV file.", e);
        }
    }
}
