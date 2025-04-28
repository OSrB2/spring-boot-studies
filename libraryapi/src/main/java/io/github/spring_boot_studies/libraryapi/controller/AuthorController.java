package io.github.spring_boot_studies.libraryapi.controller;

import io.github.spring_boot_studies.libraryapi.controller.dto.AuthorDTO;
import io.github.spring_boot_studies.libraryapi.controller.mappers.AuthorMapper;
import io.github.spring_boot_studies.libraryapi.model.Author;
import io.github.spring_boot_studies.libraryapi.service.AuthorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/authors")
@RequiredArgsConstructor // Essa annotation do Lombok gera o construtor com os parâmetros finais
// http://localhost:8080/api/authors
public class AuthorController implements GenericController {

  private final AuthorService service;
  private final AuthorMapper authorMapper;

  @PostMapping
  @PreAuthorize("hasRole('ADMIN')") // Somente usuários com ROLE de ADMIN conseguem cadastrar autores
  public ResponseEntity<Void> registerAuthor(@RequestBody @Valid AuthorDTO authorDTO) {
    Author authorEntity = authorMapper.toEntity(authorDTO); // Converte o AuthorDTO para Author
    service.registarAuthor(authorEntity);
    URI location = headerLocationGenerator(authorDTO.id());
    return ResponseEntity.created(location).build();
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasAnyRole('USER', 'ADMIN')") // Usuários com ROLE de USER ou ADMIN conseguem acessar
  public ResponseEntity<AuthorDTO> findAuthorById(@PathVariable("id") String id) {
    var idAuthor = UUID.fromString(id);

    return service
        .authorById(idAuthor)
        .map(author -> {
          AuthorDTO authorDTO = authorMapper.toDTO(author);
          return ResponseEntity.ok(authorDTO);
        }).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @GetMapping
  @PreAuthorize("hasAnyRole('USER', 'ADMIN')") // Usuários com ROLE de USER ou ADMIN conseguem acessar
  public ResponseEntity<List<AuthorDTO>> searchWithFilter(@RequestParam(value = "name", required = false) String name,
                                                          @RequestParam(value = "nationality", required = false) String nationality) {
//  List<Author> authorList = service.searchAuthorWithFilter(name, nationality);
    List<Author> authorList = service.searchByExample(name, nationality); // Utilizando example
    List<AuthorDTO> authorDTOList = authorList
        .stream()
        .map(authorMapper::toDTO) // Converte a lista de Author para AuthorDTO
        .collect(Collectors.toList());

    return ResponseEntity.ok(authorDTOList);
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')") // Somente usuários com ROLE de ADMIN conseguem cadastrar autores
  public ResponseEntity<Void> updateAuthor(@PathVariable("id") String id, @RequestBody @Valid AuthorDTO authorDTO) {
    var idAuthor = UUID.fromString(id);
    Optional<Author> authorOptional = service.authorById(idAuthor);

    if (authorOptional.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    var author = authorOptional.get();
    author.setName(authorDTO.name());
    author.setDateBirth(authorDTO.dateBirth());
    author.setNationality(authorDTO.nationality());

    service.updateAuthor(author);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')") // Somente usuários com ROLE de ADMIN conseguem cadastrar autores
  public ResponseEntity<Void> deleteAuthor(@PathVariable("id") String id) {
    var idAuthor = UUID.fromString(id);
    Optional<Author> authorOptional = service.authorById(idAuthor);

    if (authorOptional.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    service.deleteAuthorId(authorOptional.get());
    return ResponseEntity.noContent().build();
  }
}
