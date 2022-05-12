package de.workshops.bookshelf;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
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
    public void init() throws Exception {
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
}
