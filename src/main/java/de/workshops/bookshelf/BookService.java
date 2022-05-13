package de.workshops.bookshelf;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository repository;

    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    List<Book> getAllBooks() {
        return repository.getBooks();
    }

    Book getBooksByIsbn(String isbn) throws BookException {
        final var books = repository.getBooks();
        return books.stream()
                .filter(book -> book.getIsbn().equals(isbn))
                .findFirst()
                .orElseThrow(() -> new BookException("ISBN not valid"));
    }

    List<Book> getBooksByAuthor(String author)  {
        final var books = repository.getBooks();
        return books.stream().filter(book -> book.getAuthor().startsWith(author)).toList();
    }

    List<Book> searchBooks(BookSearchParameter searchParameter)  {
        final var books = repository.getBooks();
        return books.stream()
                .filter(book -> book.getAuthor().startsWith(searchParameter.getAuthorName()))
                .filter(book -> book.getTitle().startsWith(searchParameter.getTitle()))
                .toList();
    }

    Book addBook (Book newBook) {
        return repository.save(newBook);
    }
}
