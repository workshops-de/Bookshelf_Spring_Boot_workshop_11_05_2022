package de.workshops.bookshelf;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class BookController {

    private final BookService service;

    public BookController(BookService service) {
        this.service = service;
    }

    @GetMapping
    public String getAllBooks(Model model) {
        final var allBooks = service.getAllBooks();

        model.addAttribute("books", allBooks);
        return "book";
    }
}
