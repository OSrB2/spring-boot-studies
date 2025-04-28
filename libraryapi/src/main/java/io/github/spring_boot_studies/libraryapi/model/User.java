package io.github.spring_boot_studies.libraryapi.model;

import io.hypersistence.utils.hibernate.type.array.ListArrayType;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Type;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tb_user")
@Data
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(name = "login")
  private String login;

  @Column(name = "password")
  private String password;

  @Column(name = "email")
  private String email;

  @Type(ListArrayType.class) // Hibernate irá mapear a lista como um array no banco de dados
  @Column(name = "roles", columnDefinition = "varchar[]") // columDefinition define o tipo de dado no banco de dados
  private List<String> roles;
}
