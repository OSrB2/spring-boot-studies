package io.github.spring_boot_studies.libraryapi.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tb_author", schema = "public") // O schema não é obrigatório quando for public, mas é uma boa prática
@Data
@ToString(exclude = "books") // Evitando o loop infinito no toString, pois o autor tem uma lista de livros
@EntityListeners(AuditingEntityListener.class) // Anotação do Spring Data JPA para habilitar o auditoria
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

  @OneToMany(mappedBy = "author", fetch = FetchType.LAZY
  //  cascade = CascadeType.ALL
  )  // Por padrão @OneToMany é LAZY, mas é bom deixar explícito
  private List<Book> books;

  @CreatedDate // Anotação do Spring Data JPA para data de criação, ela cria a data automaticamente
  @Column(name = "date_registration")
  private LocalDateTime dateRegistration;

  @LastModifiedDate // Anotação do Spring Data JPA para data de atualização, ela cria a data automaticamente
  @Column(name = "date_update")
  private LocalDateTime dateUpdate;

  @ManyToOne
  @JoinColumn(name = "id_user")
  private User user;
}
