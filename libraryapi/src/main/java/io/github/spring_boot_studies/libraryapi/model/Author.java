package io.github.spring_boot_studies.libraryapi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tb_author", schema = "public") // O schema não é obrigatório quando for public, mas é uma boa prática
@Getter
@Setter
@ToString(exclude = "books") // Evitando o loop infinito no toString, pois o autor tem uma lista de livros
public class Author {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(name = "name", length = 100, nullable = false)
  private String name;

  @Column(name = "date_birth", nullable = false)
  private LocalDate dateBirth;

  @Column(name = "nationality", length = 50, nullable = false)
  private String nationality;

  @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.LAZY)  // Por padrão @OneToMany é LAZY, mas é bom deixar explícito
  private List<Book> books;
}
