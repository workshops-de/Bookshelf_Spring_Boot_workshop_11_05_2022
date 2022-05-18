package de.workshops.bookshelf;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class BookRestControllerTest {

    @Autowired
    BookRestController bookRestController;


    @Test
    void getAllBooks() {
        final var response = bookRestController.getAllBooks();
        final var books = response.getBody();

        assertEquals(3, books.size());

        assertThat(books).hasSize(3);
    }
}