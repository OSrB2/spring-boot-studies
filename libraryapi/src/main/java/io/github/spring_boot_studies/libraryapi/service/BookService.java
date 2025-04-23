package io.github.spring_boot_studies.libraryapi.service;

import io.github.spring_boot_studies.libraryapi.model.Book;
import io.github.spring_boot_studies.libraryapi.model.BookGenre;
import io.github.spring_boot_studies.libraryapi.repository.BookRepository;
import io.github.spring_boot_studies.libraryapi.validator.BookValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import static io.github.spring_boot_studies.libraryapi.repository.specs.BookSpecs.*;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookService {

  private final BookRepository bookRepository;
  private final BookValidator bookValidator;

  public Book registerBook(Book book) {
    bookValidator.validate(book);
    return bookRepository.save(book);
  }

  public Optional<Book> BookById(UUID id) {
    return bookRepository.findById(id);
  }

  public void deleteBook(Book book) {
    bookRepository.delete(book);
  }

  public Page<Book> searchBookWithFilter(
      String isbn,
      String title,
      String authorName,
      BookGenre gender,
      Integer publicationYear,
      Integer page,
      Integer pageSize) {
    // Specification -> Permite criar consultas mais complexas, como filtros, ordenações e paginação.

    // SELECT * FROM tb_book WHERE 0 = 0 -> para iniciar a consulta
    Specification<Book> specs = Specification.where(((
        root, query, cb) -> cb.conjunction()));

    if (isbn != null) {
      // query = query and isbn = :isbn
      specs = specs.and(isbnEqual(isbn));
    }

    if (title != null) {
      // query = query and title like %:title%
      specs = specs.and(titleLike(title));
    }

    if (gender != null) {
      specs = specs.and(genderEqual(gender));
    }

    if (publicationYear != null) {
      specs = specs.and(publicationYearEqual(publicationYear));
    }

    if (authorName != null) {
      // query = query and author.name like %:authorName%
      specs = specs.and(authorNameLike(authorName));
    }

    Pageable pageRequest = PageRequest.of(page, pageSize);

    return bookRepository.findAll(specs, pageRequest);
  }

  public void update(Book book) {
    if(book.getId() == null) {
      throw new IllegalArgumentException("Book ID cannot be null!");
    }
    bookValidator.validate(book);
    bookRepository.save(book);
  }
}
