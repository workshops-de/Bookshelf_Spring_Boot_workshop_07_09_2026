package de.workshops.bookshelf;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.client.RestTestClient;

import static java.util.Objects.requireNonNull;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureRestTestClient
class BookRestControllerRestClientTest {

    @Autowired
    RestTestClient restClient;

    @Test
    void getAllBooks() {
        restClient.get().uri("/book")
                .exchangeSuccessfully()
                .expectStatus().isOk();
    }

    @Test
    @DirtiesContext
    void saveBook() {
        int numberOfBooksBeforeSave = getBooks().length;

        Book newBook = new Book("JUnit Explained", "A short introduction to JUnit", "John Doe", "978-1234567890");
        restClient
                .post().uri("/book")
                .body(newBook)
                .exchange()
                .expectStatus().isCreated();

        int numberOfBooksAfterSave = getBooks().length;
        assertThat(numberOfBooksAfterSave).isEqualTo(numberOfBooksBeforeSave + 1);
    }

    private Book[] getBooks() {
        Book[] books = restClient
                .get().uri("/book")
                .exchangeSuccessfully()
                .returnResult(Book[].class).getResponseBody();
        return requireNonNull(books);
    }
}
