package de.workshops.bookshelf;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookJdbcRepository {

    private final JdbcTemplate template;

    public BookJdbcRepository(JdbcTemplate template) {
        this.template = template;
    }

    public List<Book> getBooks() {
        String sql = "SELECT * FROM BOOK";
        return template.query(sql, new BeanPropertyRowMapper<>(Book.class));
    }

    public Book save(Book book) {
        String sql = "INSERT INTO BOOK (title, description, author, isbn) VALUES (?, ?, ?, ?)";
        final var update = template.update(sql, book.getTitle(), book.getDescription(), book.getAuthor(), book.getIsbn());
        return book;
    }
}
