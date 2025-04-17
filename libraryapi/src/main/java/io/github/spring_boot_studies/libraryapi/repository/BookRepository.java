package io.github.spring_boot_studies.libraryapi.repository;

import io.github.spring_boot_studies.libraryapi.model.Author;
import io.github.spring_boot_studies.libraryapi.model.Book;
import io.github.spring_boot_studies.libraryapi.model.BookGenre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * @see BookRepositoryTest
 */

@Repository
public interface BookRepository extends JpaRepository<Book, UUID> {
  // Query method para buscar livro pelo id do autor
  // SELECT * FROM tb_book WHERE id_author = id
  List<Book> findByAuthor(Author author); // findBy é um padrão do Spring Data JPA para criar consultas

  // SELECT * FROM tb_book WHERE id_author = id -> Verifica se existe livro com o autor
  boolean existsByAuthor(Author author);

  // SELECT & FROM tb_book WHERE title = title
  List<Book> findByTitle(String title);

  // SELECT * FROM tb_book WHERE isbn = isbn
  List<Book> findByIsbn(String isbn);

  // SELECT * FROM tb_book WHERE title = title AND price = price
  List<Book> findByTitleAndPrice(String title, BigDecimal price);

  // SELECT * FROM tb_book WHERE title = title OR isbn = isbn
  List<Book> findByTitleOrIsbn(String title, String isbn);

  // SELECT * FROM tb_book WHERE publication_date BETWEEN startDate AND endDate
  List<Book> findByPublicationDateBetween(LocalDate startDate, LocalDate endDate);

  // JPQL -> é uma linguagem de consulta orientada a objetos, que permite consultar entidades em vez de tabelas
  // SELECT b.* FROM tb_book AS b ORDER BY b.title
  @Query("SELECT b FROM Book AS b ORDER BY b.title")
  List<Book> listAllBooksOrderByTitle();

  /**
   * SELECT a.*
   * FROM Book b
   * JOIN Author a ON a.id = b.id_author
   */
  @Query("SELECT a FROM Book b JOIN b.author a")
  List<Author> listAuthorsFromBooks();

  // SELECT DISTINCT b.* FROM book
  @Query("SELECT DISTINCT b.title FROM Book b")
  List<String> listAllDiferentNamesFromBooks();

  @Query("""
      SELECT b.gender
      FROM Book b
      JOIN b.author a
      WHERE a.nationality = 'British'
      ORDER BY b.gender
      """)
  List<String> listGenderAuthorBritish();

  // Query com parâmetros
  @Query("SELECT b FROM Book b WHERE b.gender = :bookGenre")
  List<Book> findByGender(@Param("bookGenre") BookGenre bookGenre);

  // Positional parameters
  @Query("SELECT b FROM Book b WHERE b.gender = ?1 order by ?2")
  List<Book> findByGenderPositionalParameters(BookGenre bookGenre, String namePropertie);

  @Modifying // Anotação para indicar que a consulta vai modificar o banco de dados
  @Transactional // Anotação para indicar que a consulta vai ser feita dentro de uma transação
  @Query("DELETE FROM Book WHERE gender = ?1")
  void deleteByGender(BookGenre bookGenre);

  @Modifying
  @Transactional
  @Query("UPDATE Book SET publicationDate = ?1") // Atualiza todos os livros com a nova data de publicação. Esse update NÃO tem WHERE, apenas para estudo, o WHERE é obrigatório.
  void updatePublicationDate(LocalDate newDate);
}
