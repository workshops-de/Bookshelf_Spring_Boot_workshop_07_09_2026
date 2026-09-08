package de.workshops.bookshelf;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class BookSearchRequest {

    @Size(min = 3)
    private String author;

    private String isbn;

}
