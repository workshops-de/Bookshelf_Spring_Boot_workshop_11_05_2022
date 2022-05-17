package de.workshops.bookshelf;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class BookJpaRepositoryTest {

    @Autowired
    BookJpaRepository repository;

    @Test
    void testFindBookByIsbn() {
        String isbn = "978-3836211161";
        final var book = repository.findByIsbn(isbn);
        assertThat(book)
                .isNotNull()
                .hasFieldOrPropertyWithValue("author", "Gottfried Wolmeringer");
    }
}