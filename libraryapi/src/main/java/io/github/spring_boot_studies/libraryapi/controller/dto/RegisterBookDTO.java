package io.github.spring_boot_studies.libraryapi.controller.dto;

import io.github.spring_boot_studies.libraryapi.model.BookGenre;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.hibernate.validator.constraints.ISBN;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record RegisterBookDTO(
    @ISBN(message = "Must be a valid ISBN!")
    @NotBlank(message = "Is required!")
    String isbn,
    @NotBlank(message = "Is required!")
    String title,
    @NotNull(message = "Is required!")
    @Past(message = "Must be a past date!")
    LocalDate publicationDate,
    BookGenre gender,
    BigDecimal price,
    @NotNull(message = "Is required!")
    UUID idAuthor) {
}
