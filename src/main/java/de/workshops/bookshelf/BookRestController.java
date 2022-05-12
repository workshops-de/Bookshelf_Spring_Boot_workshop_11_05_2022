package de.workshops.bookshelf;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookRestController {

    private final ResourceLoader resourceLoader;
    private final ObjectMapper mapper;

    private List<Book> books;

    public BookRestController(ResourceLoader resourceLoader, ObjectMapper mapper) {
        this.resourceLoader = resourceLoader;
        this.mapper = mapper;
    }

    @PostConstruct
    public void init() throws IOException {
        final var resource = resourceLoader.getResource("classpath:books.json");
        this.books = mapper.readValue(resource.getInputStream(), new TypeReference<>() {});
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        if (books.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(books);
    }

    @GetMapping(produces = "plain/text")
    public String getAllBooksAsText() {
        return books.toString();
    }

    @GetMapping("/{isbn}")
    public ResponseEntity<Book> getByIsbn(@PathVariable String isbn) {
        final var bookStream = books.stream().filter(book -> book.getIsbn().equals(isbn));
        final var foundBook = bookStream.findFirst();

        if (foundBook.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(foundBook.get());
    }
    @GetMapping(params = "author")
    public ResponseEntity<List<Book>> getByAuthor(@RequestParam String author) {
        final var foundBooks = books.stream().filter(book -> book.getAuthor().startsWith(author)).toList();
        if (foundBooks.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(foundBooks);
    }

    @PostMapping
    public ResponseEntity<Book> createBook (@RequestBody Book book) {
        books.add(book);
        return ResponseEntity.ok(book);
    }

    @PostMapping("/search")
    public ResponseEntity<List<Book>> searchBook (@RequestBody BookSearchParameter searchParameter) {
        final var foundBooks = books.stream()
                .filter(book -> book.getAuthor().startsWith(searchParameter.getAuthorName()))
                .filter(book -> book.getTitle().startsWith(searchParameter.getTitle()))
                .toList();

        if (foundBooks.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(foundBooks);
    }
}
