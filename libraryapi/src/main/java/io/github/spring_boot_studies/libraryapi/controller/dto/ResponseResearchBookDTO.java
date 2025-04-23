package io.github.spring_boot_studies.libraryapi.controller.dto;

import io.github.spring_boot_studies.libraryapi.model.BookGenre;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record ResponseResearchBookDTO(
    UUID id,
    String isbn,
    String title,
    LocalDate publicationDate,
    BookGenre gender,
    BigDecimal price,
    AuthorDTO author) {
}
