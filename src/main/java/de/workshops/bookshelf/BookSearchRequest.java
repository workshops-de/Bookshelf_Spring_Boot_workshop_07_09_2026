package de.workshops.bookshelf;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class BookSearchRequest {

    @NotBlank
    private String author;

    private String isbn;

}
