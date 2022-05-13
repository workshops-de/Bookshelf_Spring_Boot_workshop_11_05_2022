package de.workshops.bookshelf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookRestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BookRestController.class);

    private final BookService service;

    public BookRestController(BookService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        final var allBooks = service.getAllBooks();
        if (allBooks.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(allBooks);
    }

    @GetMapping(produces = "plain/text")
    public String getAllBooksAsText() {
        return service.getAllBooks().toString();
    }

    @GetMapping("/{isbn}")
    public ResponseEntity<Book> getByIsbn(@PathVariable String isbn) throws BookException {
        final var foundBook = service.getBooksByIsbn(isbn);
        return ResponseEntity.ok(foundBook);
    }

    @GetMapping(params = "author")
    public ResponseEntity<List<Book>> getByAuthor(@RequestParam String author) {
        final var foundBooks = service.getBooksByAuthor(author);
        if (foundBooks.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(foundBooks);
    }

    @PostMapping
    public ResponseEntity<Book> createBook (@RequestBody Book book, HttpServletRequest request) {
        LOGGER.info("Method Info: {}", request.getMethod());
        LOGGER.info("Path Info: {}", request.getRequestURI());

        service.addBook(book);
        return ResponseEntity.ok(book);
    }

    @PostMapping("/search")
    public ResponseEntity<List<Book>> searchBook (@RequestBody @Valid BookSearchParameter searchParameter) {
        final var foundBooks = service.searchBooks(searchParameter);

        if (foundBooks.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(foundBooks);
    }

    @ExceptionHandler(BookException.class)
    public ResponseEntity<String> handleBookException(BookException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.I_AM_A_TEAPOT);
    }
}
