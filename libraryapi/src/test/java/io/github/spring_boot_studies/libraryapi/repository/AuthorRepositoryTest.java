package io.github.spring_boot_studies.libraryapi.repository;

import io.github.spring_boot_studies.libraryapi.model.Author;
import io.github.spring_boot_studies.libraryapi.model.Book;
import io.github.spring_boot_studies.libraryapi.model.BookGenre;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class AuthorRepositoryTest {

  @Autowired
  AuthorRepository authorRepository;

  @Autowired
  BookRepository bookRepository;

  @Test
  public void saveAuthorTest() {
    Author author = new Author();
    author.setName("Douglas Adams");
    author.setDateBirth(LocalDate.of(1952, 3, 11));
    author.setNationality("British");

    var saveAuthor = authorRepository.save(author);
    System.out.println("Author saved successfully! \n" + saveAuthor);
  }

  @Test
  public void authorUpdate() {
    var id = UUID.fromString("f4925c24-4e64-428c-b5b5-471c65f85055");

    Optional<Author> authorExist = authorRepository.findById(id);

    if (authorExist.isPresent()) {
      Author authorFound = authorExist.get();
      System.out.println("Author");
      System.out.println(authorExist);

      authorFound.setNationality("English");

      authorRepository.save(authorFound);
    }
  }

  @Test
  public void listAllAuthorsTest() {
    List<Author> authorList = authorRepository.findAll();
    authorList.forEach(System.out::println);
  }

  @Test
  public void countAuthorsTest() {
    long count = authorRepository.count();
    System.out.println("Total authors: " + count);
  }

  @Test
  public void deleteAuthorByIdTest() {
    var id = UUID.fromString("f4925c24-4e64-428c-b5b5-471c65f85055");

    authorRepository.deleteById(id);
  }

  @Test
  public void deleteObjectAuthorTest() {
    var id = UUID.fromString("c86d482b-7b18-4366-b222-d68411f7de36");
    var douglas = authorRepository.findById(id).get();
    authorRepository.delete(douglas);
  }

  @Test
  void saveAuthorWithBooksTest() {
    Author author = new Author();
    author.setName("Julio Verne");
    author.setNationality("French");
    author.setDateBirth(LocalDate.of(1828, 2, 8));

    Book book = new Book();
    book.setTitle("1000 Leagues Under the Sea");
    book.setPublicationDate(LocalDate.of(1870, 1, 1));
    book.setIsbn("978-0-7475-3271-9");
    book.setGender(BookGenre.FANTASY);
    book.setPrice(BigDecimal.valueOf(150.00));
    book.setAuthor(author);

    Book book2 = new Book();
    book2.setTitle("Journey to the Center of the Earth");
    book2.setPublicationDate(LocalDate.of(1864, 1, 1));
    book2.setIsbn("978-0-7475-3271-9");
    book2.setGender(BookGenre.FANTASY);
    book2.setPrice(BigDecimal.valueOf(120.00));
    book2.setAuthor(author);

    author.setBooks(new ArrayList<>());
    author.getBooks().add(book);
    author.getBooks().add(book2);

    authorRepository.save(author);
  }

  @Test
  void listAllBooksAuthor() {
    var id = UUID.fromString("65b59ddd-6261-4374-96dc-a8cacae2400e");
    var author = authorRepository.findById(id).get();

    List<Book> bookList = bookRepository.findByAuthor(author);
    author.setBooks(bookList);
    author.getBooks().forEach(System.out::println);
  }
}
