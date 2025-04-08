package io.github.spring_boot_studies.arquiteturaSpring.toDos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // @Repository otimiza a classe para ser um repositório Spring Data JPA e trabalha com a persistência de dados
public interface TodoRepository extends JpaRepository<TodoEntity, Integer> {

  // Método para verificar se existe um ToDo com a descrição fornecida
  boolean existsByDescription(String description);
}
