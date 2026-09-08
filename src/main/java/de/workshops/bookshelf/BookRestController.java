package de.workshops.bookshelf;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/book")
@Validated
public class BookRestController {

    private final BookService service;

    public BookRestController(BookService service) {
        this.service = service;
    }

    @GetMapping
    public List<Book> getAllBooks() {
        return service.getAllBooks();
    }

    @GetMapping("/{isbn}")
    public Book getSingleBook(@PathVariable String isbn) {
        return service.getSingleBook(isbn);
    }

    @GetMapping(params = "author")
    public List<Book> searchBooksByAuthor(@RequestParam("author") @NotBlank @Size(min = 3) String author) {
        return service.searchBooksByAuthor(author);
    }

    @PostMapping("/search")
    public List<Book> searchBooks(@RequestBody @Valid BookSearchRequest request) {
        return service.searchBooks(request);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public void saveBook(@RequestBody @Valid Book book) {
        service.saveBook(book);
    }

    @ExceptionHandler
    public ResponseEntity<String> handle(BookNotFoundException e) {
        return ResponseEntity.status(NOT_FOUND).body("Buch nicht gefunden - " + e.getMessage());
    }
}
