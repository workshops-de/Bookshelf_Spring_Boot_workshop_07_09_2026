package de.workshops.bookshelf.book;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.validation.annotation.Validated;

import java.net.URI;

@Validated
@ConfigurationProperties("bookshelf")
@Getter
@Setter
public class BoookshelfProperties {

    @NotBlank
    private String owner;

    @NestedConfigurationProperty
    private IsbnLookup isbnLookup = new IsbnLookup();

    @Getter
    @Setter
    public static class IsbnLookup {

        @NotBlank
        private URI url;

        @NotBlank
        private String apiKey;
    }
}
