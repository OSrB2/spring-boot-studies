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
import java.util.List;
import java.util.UUID;

@SpringBootTest
public class BookRepositoryTest {

  @Autowired
  BookRepository bookRepository;

  @Autowired
  AuthorRepository authorRepository;

  @Test
  void saveBookTest() {
    Author author = authorRepository.findById(UUID.fromString("4a1d9cad-8072-496c-8093-9e848a3c3887")).orElse(null);
    Book book = new Book();
    book.setTitle("The Hitchhiker's Guide to the Galaxy");
    book.setAuthor(author);
    book.setPublicationDate(LocalDate.of(1979, 10, 12));
    book.setIsbn("978-0-330-25864-7");
    book.setPrice(BigDecimal.valueOf(320.80));
    book.setGender(BookGenre.FANTASY);

    bookRepository.save(book);
  }

  @Test
  void saveCascadeBookTest() {

    Author author = new Author();
    author.setName("J.K. Rowling");
    author.setDateBirth( LocalDate.of(1965, 7, 31));
    author.setNationality("British");

    Book book = new Book();
    book.setTitle("Harry Potter and the Philosopher's Stone");
    book.setAuthor(author);
    book.setPublicationDate(LocalDate.of(1997, 6, 26));
    book.setIsbn("978-0-7475-3271-9");
    book.setPrice(BigDecimal.valueOf(320.80));
    book.setGender(BookGenre.FANTASY);

    bookRepository.save(book);
  }

  @Test
  void saveAuthorAndBookTest() {

    Author author = new Author();
    author.setName("Isaac Asimov");
    author.setDateBirth(LocalDate.of(1920, 1, 2));
    author.setNationality("American");

    Book book = new Book();
    book.setTitle("Foundation");
    book.setAuthor(author);
    book.setPublicationDate(LocalDate.of(1951, 11, 1));
    book.setIsbn("978-0-553-80371-0");
    book.setPrice(BigDecimal.valueOf(320.80));
    book.setGender(BookGenre.FICTION);

    authorRepository.save(author);
    bookRepository.save(book);
  }

  @Test
  void updateAuthorOfBookTest() {
    UUID id = UUID.fromString("107c93f3-7e05-4817-9643-0a12d3e7bafa");
    var bookUpdate = bookRepository.findById(id).orElse(null);

    UUID idAuthor =  UUID.fromString("2bf4189b-be52-4297-a788-1c86d71063a1");
    Author newAuthor = authorRepository.findById(idAuthor).orElse(null);

    bookUpdate.setAuthor(newAuthor);

    bookRepository.save(bookUpdate);
  }

  @Test
  void deleteBookByIDTest() {
    UUID id = UUID.fromString("107c93f3-7e05-4817-9643-0a12d3e7bafa");
    bookRepository.deleteById(id);
  }

  @Test
  @Transactional // Evita o erro de LazyInitializationException, além de garantir que a transação seja feita corretamente
  void findBookByIDTest() {
    UUID id = UUID.fromString("9abc67d7-08c1-4189-816a-d09d930c968c");
    Book book = bookRepository.findById(id).orElse(null);
    System.out.println("Book: " + book.getTitle());
    System.out.println("Author: " + book.getAuthor().getName());
  }

  @Test
  void findByTitleTest() {
    List<Book> bookList = bookRepository.findByTitle("Journey to the Center of the Earth");
    bookList.forEach(System.out::println);
  }

  @Test
  void findByISBNTest() {
    List<Book> bookList = bookRepository.findByIsbn("978-0-7475-3271-9");
    bookList.forEach(System.out::println);
  }

  @Test
  void findByTitleAndPriceTest() {
    List<Book> bookList = bookRepository.findByTitleAndPrice("Journey to the Center of the Earth", BigDecimal.valueOf(120.00));
    bookList.forEach(System.out::println);
  }

  @Test
  void findByTitleOrISBNTest() {
    List<Book> bookList = bookRepository.findByTitleOrIsbn("Journey to the Center of the Earth", "978-0-7475-3271-9");
    bookList.forEach(System.out::println);
  }

  @Test
  void findByPublicationDateBetweenTest() {
    List<Book> bookList = bookRepository.findByPublicationDateBetween(LocalDate.of(1864, 1, 1), LocalDate.of(1865, 1, 1));
    bookList.forEach(System.out::println);
  }

  // JPQL
  @Test
  void listAllBooksOrderByTitleTest() {
    var result = bookRepository.listAllBooksOrderByTitle();
    result.forEach(System.out::println);
  }

  @Test
  void listAuthorsFromBooks(){
    var result = bookRepository.listAuthorsFromBooks();
    result.forEach(System.out::println);
  }

  @Test
  void listAllDiferentNamesFromBooks() {
    var result = bookRepository.listAllDiferentNamesFromBooks();
    result.forEach(System.out::println);
  }

  @Test
  void listGenderAuthorBritish() {
    var result = bookRepository.listGenderAuthorBritish();
    result.forEach(System.out::println);
  }

  @Test
  void findByGenderTest() {
    var result = bookRepository.findByGender(BookGenre.FICTION);
    result.forEach(System.out::println);
  }

  @Test
  void findByGenderPositionalParameterTest() {
    var result = bookRepository.findByGenderPositionalParameters(BookGenre.FICTION, "title");
    result.forEach(System.out::println);
  }

  @Test
  void deleteByGender() {
    bookRepository.deleteByGender(BookGenre.FICTION);
  }

  @Test
  void updatePublicationDate() {
    bookRepository.updatePublicationDate(LocalDate.of(2000, 1, 1));
  }
}