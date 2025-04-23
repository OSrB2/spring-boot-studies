package io.github.spring_boot_studies.libraryapi.repository.specs;

import io.github.spring_boot_studies.libraryapi.model.Book;
import io.github.spring_boot_studies.libraryapi.model.BookGenre;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public class BookSpecs {

  // É chamado quando o usuário insere o isbn para filtrar os livros. WHERE isbn = :isbn, cb == criteriaBuilder
  public static Specification<Book> isbnEqual(String isbn) {
    return (root, query, cb) -> cb.equal(root.get("isbn"), isbn);
  }

  // É chamado quando o usuário insere o título para filtrar os livros. WHERE title LIKE %:title%
  public static Specification<Book> titleLike(String title) {
    return (root, query, cb) ->
        cb.like(cb.upper(root.get("title")), "%" + title.toUpperCase() + "%");
  }

  // É chamado quando o usuário insere o genero para filtrar os livros. WHERE gender = :gender
  public static Specification<Book> genderEqual(BookGenre gender) {
    return (root, query, cb) ->
        cb.equal(root.get("gender"), gender);
  }

  // Esse método é chamado quando o usuário insere o ano de publicação para filtrar os livros.
  // O método to_char é usado para converter a data de publicação em uma string no formato 'YYYY'.
  // Isso é necessário porque o banco de dados pode armazenar a data em um formato diferente.
  // O método cb.function é usado para chamar a função to_char no banco de dados.
  // O método cb.literal é usado para passar o formato 'YYYY' como um argumento literal.
  // O método cb.equal é usado para comparar a string resultante da função to_char com o ano de publicação fornecido.
  // SELECT TO_CHAR(publication_date, 'YYYY') = :publicationYear
  public static Specification<Book> publicationYearEqual(Integer publicationYear) {
    return (root, query, cb) ->
        cb.equal(cb.function("to_char",
            String.class,
            root.get("publicationDate"),
            cb.literal("YYYY")),
            publicationYear.toString());
  }

  public static Specification<Book> authorNameLike(String authorName) {
    return (root, query, cb) -> {
//      return cb.like(cb.upper(root.get("author").get("name")),
//          "%" + authorName.toUpperCase() + "%");

      // Usando Join
      Join<Object, Object> joinAuthor = root.join("author", JoinType.LEFT);

      return cb.like(cb.upper(joinAuthor.get("name")), "%" + authorName.toUpperCase() + "%");

    };
  }
}
