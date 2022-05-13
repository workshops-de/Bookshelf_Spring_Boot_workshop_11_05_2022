package de.workshops.bookshelf;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

@Repository
public class BookRepository {
    private final ResourceLoader resourceLoader;
    private final ObjectMapper mapper;

    public BookRepository(ResourceLoader resourceLoader, ObjectMapper mapper) {
        this.resourceLoader = resourceLoader;
        this.mapper = mapper;
    }

    private List<Book> books;

    @PostConstruct
    public void init() throws IOException {
        final var resource = resourceLoader.getResource("classpath:books.json");
        this.books = mapper.readValue(resource.getInputStream(), new TypeReference<>() {});
    }

    public List<Book> getBooks() {
        return books;
    }

    public Book save(Book book) {
        books.add(book);
        return book;
    }
}
