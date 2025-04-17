package io.github.spring_boot_studies.libraryapi.validator;

import io.github.spring_boot_studies.libraryapi.exception.DuplicateRecordException;
import io.github.spring_boot_studies.libraryapi.model.Author;
import io.github.spring_boot_studies.libraryapi.repository.AuthorRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthorValidator {

  private AuthorRepository repository;

  public AuthorValidator(AuthorRepository authorRepository) {
    this.repository = authorRepository;
  }

  public void validate(Author author) {
    if (authorExists(author)) {
      throw new DuplicateRecordException("Author already exists");
    }
  }

  private boolean authorExists(Author author) {
    Optional<Author> authorOptional = repository.findByNameAndDateBirthAndNationality(author.getName(),
        author.getDateBirth(),
        author.getNationality());
    // Verifica se o autor não tem id, ou seja, é um novo autor.
    if (author.getId() == null) {
      return authorOptional.isPresent();
    }

    // Verifica se o autor já existe no banco de dados, mas não é o mesmo autor que está sendo atualizado.
    return !author.getId().equals(authorOptional.get().getId()) && authorOptional.isPresent();
  }
}
