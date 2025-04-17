package io.github.spring_boot_studies.libraryapi.repository;

import io.github.spring_boot_studies.libraryapi.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AuthorRepository extends JpaRepository<Author, UUID> {

  List<Author> findByName(String name);
  List<Author> findByNationality(String nationality);
  List<Author> findByNameAndNationality(String name, String nationality);

  // Verifica se o autor já existe no banco de dados.
  Optional<Author> findByNameAndDateBirthAndNationality(String name, LocalDate dateBirth, String nationality);
}
