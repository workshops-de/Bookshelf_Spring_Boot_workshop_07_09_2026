package de.workshops.bookshelf;

import io.swagger.v3.oas.models.OpenAPI;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.client.RestTestClient;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureRestTestClient
class SwaggerConfigurationTest {

    @Autowired
    RestTestClient http;

    @Autowired(required = false)
    OpenAPI openAPI;

    @Nested
    class ByDefault {

        @Test
        void swagger_ui_is_available() {
            http
                    .get().uri("/swagger-ui/index.html").exchange()
                    .expectStatus().isOk();
        }

        @Test
        void api_info_is_available() {
            assertThat(openAPI).isNotNull();
        }
    }

    @Nested
    @ActiveProfiles("prod")
    class InProd {

        @Test
        void swagger_ui_is_not_available() {
            http
                    .get().uri("/swagger-ui/index.html").exchange()
                    .expectStatus().isNotFound();
        }

        @Test
        void api_info_is_not_available() {
            assertThat(openAPI).isNull();
        }
    }
}
