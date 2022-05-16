package de.workshops.bookshelf;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TestWithMockito {

    @InjectMocks
    BookRestController bookRestController;

    @Mock
    BookService bookService;

    @Test
    void getAllBooks() {

        when(bookService.getAllBooks()).thenReturn(List.of());

        final var allBooks = bookRestController.getAllBooks();

        assertThat(allBooks).extracting(ResponseEntity::getStatusCode).isEqualTo(HttpStatus.NO_CONTENT);

        final var body = allBooks.getBody();
        assertThat(body).isNull();
    }
}
