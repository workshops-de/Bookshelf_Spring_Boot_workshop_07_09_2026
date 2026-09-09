package de.workshops.bookshelf;

import org.springframework.boot.jackson.autoconfigure.JsonMapperBuilderCustomizer;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import static tools.jackson.databind.SerializationFeature.INDENT_OUTPUT;

@TestConfiguration
public class JsonTestConfiguration {

    @Bean
    public JsonMapperBuilderCustomizer jsonCustomizer() {
        return builder -> builder.enable(INDENT_OUTPUT);
    }
}
