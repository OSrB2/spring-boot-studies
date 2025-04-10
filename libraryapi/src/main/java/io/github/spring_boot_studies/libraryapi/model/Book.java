package io.github.spring_boot_studies.libraryapi.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "tb_book", schema = "public") // O schema não é obrigatório quando for public, mas é uma boa prática
@Data // Usando @Data do Lombok para gerar os métodos getters e setters automaticamente, e também o toString
@ToString(exclude = "author") // Evitando o loop infinito no toString, pois o autor tem uma lista de livros
public class Book {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(name = "isbn", length = 20, nullable = false)
  private String isbn;

  @Column(name = "title", length = 150 , nullable = false)
  private String title;

  @Column(name = "publication_date", nullable = false)
  private LocalDate publicationDate;

  @Enumerated(EnumType.STRING)
  @Column(name = "gender", length = 30, nullable = false)
  private BookGenre gender;

  @Column(name = "price", precision = 18, scale = 2)
  private BigDecimal price;

  //(cascade = CascadeType.ALL) // Evite usar cascade pois pode causar problemas
  @ManyToOne(fetch = FetchType.LAZY) // EAGER traz o autor junto com o livro, LAZY traz apenas o id do autor
  @JoinColumn(name = "id_author")
  private Author author;
}
