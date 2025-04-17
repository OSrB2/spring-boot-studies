package io.github.spring_boot_studies.libraryapi.controller.dto;

// Data Transfer Object -> É um padrão de projeto que tem como objetivo transferir dados entre sistemas.

import io.github.spring_boot_studies.libraryapi.model.Author;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;

public record AuthorDTO(
    UUID id,
    @NotBlank(message = "Is required!")
    @Size(min = 2, max = 100, message = "Must have between 2 and 100 characters!")
    String name,
    @NotNull(message = "Is required!")
    @Past(message = "Date must be in the past!")
    LocalDate dateBirth,
    @NotBlank(message = "Is required!")
    @Size(min = 2, max = 50, message = "Must have between 2 and 50 characters!")
    String nationality) {

  public Author mapperToAuthor() {
    Author author = new Author();
    author.setName(this.name);
    author.setDateBirth(this.dateBirth);
    author.setNationality(this.nationality);

    return author;
  }
}
