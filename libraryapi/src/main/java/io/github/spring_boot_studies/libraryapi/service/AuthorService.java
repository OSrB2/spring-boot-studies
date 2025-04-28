package io.github.spring_boot_studies.libraryapi.service;

import io.github.spring_boot_studies.libraryapi.exception.OperationNotPermittedException;
import io.github.spring_boot_studies.libraryapi.model.Author;
import io.github.spring_boot_studies.libraryapi.model.User;
import io.github.spring_boot_studies.libraryapi.repository.AuthorRepository;
import io.github.spring_boot_studies.libraryapi.repository.BookRepository;
import io.github.spring_boot_studies.libraryapi.security.SecurityService;
import io.github.spring_boot_studies.libraryapi.validator.AuthorValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor // Essa annotation gera o construtor com todos os atributos finais da classe.
public class AuthorService {

  private final AuthorRepository repository;
  private final AuthorValidator validator;
  private final BookRepository bookRepository;
  private final SecurityService securityService;

// Construtor padrão, sem a annotation @RequiredArgsConstructor
//  public AuthorService(AuthorRepository repository, AuthorValidator validator, BookRepository bookRepository) {
//    this.repository = repository;
//    this.validator = validator;
//    this.bookRepository = bookRepository;
//  }

  public Author registarAuthor(Author author) {
    validator.validate(author);
    User user = securityService.getLoggedUser();
    author.setUser(user);
    return repository.save(author);
  }

  public Optional<Author> authorById(UUID id) {
    return repository.findById(id);
  }

  public List<Author> searchAuthorWithFilter(String name, String nationality) {
    if (name != null && nationality != null) {
      return repository.findByNameAndNationality(name, nationality);
    }

    if (name != null) {
      return repository.findByName(name);
    }

    if (nationality != null) {
      return repository.findByNationality(nationality);
    }

    return repository.findAll();
  }

  public List<Author> searchByExample(String name, String nationality) {
    var author = new Author();
    author.setName(name);
    author.setNationality(nationality);

    // Classe example do Spring Data JPA, que permite criar consultas dinâmicas com base em um objeto de exemplo.
    ExampleMatcher matcher = ExampleMatcher
        .matching() // Cria um exemplo de autor com os dados passados como parâmetro.
        .withIgnoreNullValues() // Ignora os valores nulos do objeto de exemplo.
        .withIgnoreCase() // Ignora a diferença entre maiúsculas e minúsculas.
        .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING); // StringMatcher.CONTAINING -> Faz uma busca parcial, ou seja, não precisa ser exatamente igual ao que está no banco de dados.
    Example<Author> authorExample = Example.of(author, matcher); // Cria u exemplo de autor com os dados passados como parâmetro.

    return repository.findAll(authorExample);
  }

  public void updateAuthor(Author author) {

    if (author.getId() == null) {
      throw new IllegalArgumentException("Author ID cannot be null!");
    }
    validator.validate(author);
    repository.save(author);
  }

  public void deleteAuthorId(Author author) {
    if (authorHasBook(author)) {
      throw new OperationNotPermittedException("Author has books registered!");
    }
    repository.delete(author);
  }

  public boolean authorHasBook(Author author) {
    return bookRepository.existsByAuthor(author);
  }
}
