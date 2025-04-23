package io.github.spring_boot_studies.libraryapi.validator;

import io.github.spring_boot_studies.libraryapi.exception.DuplicateRecordException;
import io.github.spring_boot_studies.libraryapi.exception.InvalidFieldException;
import io.github.spring_boot_studies.libraryapi.model.Book;
import io.github.spring_boot_studies.libraryapi.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BookValidator {

  private static final int YEAR_THRESHOLD = 2020; // Usando uma constante para o ano limite

  private final BookRepository bookRepository;

  public void validate(Book book) {
    if (isbnExists(book)) {
      throw new DuplicateRecordException("This ISBN already exists");
    }

    if (isMandatoryPriceIsNull(book)) {
      throw new InvalidFieldException("price", "Price is mandatory for books published after 2020");
    }
  }

  private boolean isMandatoryPriceIsNull(Book book) {
    return book.getPrice() == null && book.getPublicationDate().getYear() > YEAR_THRESHOLD;
  }

  private boolean isbnExists(Book book) {
    Optional<Book> byIsbn = bookRepository.findByIsbn(book.getIsbn());

    // Verifica se o livro não tem id, ou seja, é um novo livro.
    if (book.getId() == null) {
      return byIsbn.isPresent();
    }
    return byIsbn // Verifica se o livro já existe no banco de dados, mas não é o mesmo livro que está sendo atualizado.
        .map(Book::getId)
        .stream()
        .anyMatch(id -> !id.equals(book.getId()));
  }
}
