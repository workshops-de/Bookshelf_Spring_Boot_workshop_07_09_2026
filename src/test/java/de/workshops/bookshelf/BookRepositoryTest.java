package de.workshops.bookshelf;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class BookRepositoryTest {

    @Autowired
    BookRepository repository;

    @Test
    void getAllBooks() {
        List<Book> books = repository.findAll();
        assertThat(books).isNotEmpty();
    }

    @Test
    void saveBook() {
        int numberOfBooksBeforeSave = repository.findAll().size();

        repository.save(BookTestData.book());

        int numberOfBooksAfterSave = repository.findAll().size();
        assertThat(numberOfBooksAfterSave).isEqualTo(numberOfBooksBeforeSave + 1);
    }
}
