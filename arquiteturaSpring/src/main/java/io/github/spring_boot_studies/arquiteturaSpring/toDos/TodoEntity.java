package io.github.spring_boot_studies.arquiteturaSpring.toDos;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_todo")
public class TodoEntity {
  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY) // IDENTITY para auto incremento e chave primária
  private Integer id;
  @Column(name = "description")
  private String description;
  @Column(name = "flag_completed")
  private Boolean completed;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Boolean getCompleted() {
    return completed;
  }

  public void setCompleted(Boolean completed) {
    this.completed = completed;
  }
}
