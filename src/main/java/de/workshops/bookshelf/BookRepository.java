package de.workshops.bookshelf;

import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

@Repository
public class BookRepository {

    private final List<Book> books;

    public BookRepository(ObjectMapper mapper, ResourceLoader resourceLoader) throws IOException {
        final var resource = resourceLoader.getResource("classpath:books.json");
        this.books = mapper.readValue(resource.getInputStream(), new TypeReference<>() {
        });
    }

    public List<Book> getAllBooks() {
        return books;
    }

    public void saveBook(Book book) {
        books.add(book);
    }
}
