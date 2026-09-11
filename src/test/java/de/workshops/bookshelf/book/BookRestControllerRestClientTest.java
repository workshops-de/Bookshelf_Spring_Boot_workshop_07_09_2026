package de.workshops.bookshelf.book;

import de.workshops.bookshelf.user.User;
import de.workshops.bookshelf.user.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.client.RestTestClient;

import static java.util.Objects.requireNonNull;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureRestTestClient
class BookRestControllerRestClientTest {

    RestTestClient restClient;

    User admin;

    @BeforeEach
    void setUp(
            @Autowired UserRepository userRepository,
            @Autowired PasswordEncoder passwordEncoder,
            @Autowired RestTestClient restClient
    ) {
        String username = "user";
        String password = "password";

        admin = new User();
        admin.setUsername(username);
        admin.setPassword(passwordEncoder.encode(password));
        admin.setRole("ROLE_ADMIN");
        admin = userRepository.save(admin);

        this.restClient = restClient.mutate()
                .requestInterceptor(new BasicAuthenticationInterceptor(username, password))
                .build();
    }

    @AfterEach
    void tearDown(
            @Autowired UserRepository userRepository
    ) {
        userRepository.delete(admin);
    }

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
