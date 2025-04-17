package io.github.spring_boot_studies.libraryapi.controller;

import io.github.spring_boot_studies.libraryapi.controller.dto.AuthorDTO;
import io.github.spring_boot_studies.libraryapi.controller.dto.ResponseError;
import io.github.spring_boot_studies.libraryapi.exception.DuplicateRecordException;
import io.github.spring_boot_studies.libraryapi.exception.OperationNotPermittedException;
import io.github.spring_boot_studies.libraryapi.model.Author;
import io.github.spring_boot_studies.libraryapi.service.AuthorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/authors")
@RequiredArgsConstructor // Essa annotation do Lombok gera o construtor com os parâmetros finais
// http://localhost:8080/api/authors
public class AuthorController {

  private final AuthorService service;

  @PostMapping
  public ResponseEntity<?> registerAuthor(@RequestBody @Valid AuthorDTO author) {
    try {
      var authorEntity = author.mapperToAuthor();
      service.registarAuthor(authorEntity);

      URI location = ServletUriComponentsBuilder.fromCurrentRequest() // Pega a URI atual, adiciona o id do autor e retorna a URI
          .path("/{id}")
          .buildAndExpand(authorEntity.getId())
          .toUri();

      return ResponseEntity.created(location).build();
    } catch (DuplicateRecordException e) {
      var errorDTO = ResponseError.conflict(e.getMessage());
      return ResponseEntity.status(errorDTO.status()).body(errorDTO);
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<AuthorDTO> findAuthorById(@PathVariable("id") String id) {
    var idAuthor = UUID.fromString(id);
    Optional<Author> authorOptional =  service.authorById(idAuthor);

    if (authorOptional.isPresent()) {
      Author author = authorOptional.get();
      AuthorDTO authorDTO = new AuthorDTO(author.getId(),
          author.getName(),
          author.getDateBirth(),
          author.getNationality());
      return ResponseEntity.ok(authorDTO);
    }
    return ResponseEntity.notFound().build();
  }

  @GetMapping
  public ResponseEntity<List<AuthorDTO>> searchWithFilter(@RequestParam(value = "name", required = false) String name,
                                                @RequestParam(value = "nationality", required = false) String nationality) {
//  List<Author> authorList = service.searchAuthorWithFilter(name, nationality);
    List<Author> authorList = service.searchByExample(name, nationality); // Utilizando example
    List<AuthorDTO> authorDTOList = authorList.stream().map(author -> new AuthorDTO(author.getId(), // O stream().map() faz a conversão de Author para AuthorDTO
        author.getName(),
        author.getDateBirth(),
        author.getNationality())).collect(Collectors.toList());

    return ResponseEntity.ok(authorDTOList);
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updateAuthor(@PathVariable("id") String id, @RequestBody @Valid AuthorDTO authorDTO) {
    try {
      var idAuthor = UUID.fromString(id);
      Optional<Author> authorOptional =  service.authorById(idAuthor);

      if (authorOptional.isEmpty()) {
        return ResponseEntity.notFound().build();
      }
      var author = authorOptional.get();
      author.setName(authorDTO.name());
      author.setDateBirth(authorDTO.dateBirth());
      author.setNationality(authorDTO.nationality());

      service.updateAuthor(author);
      return ResponseEntity.noContent().build();
    } catch (DuplicateRecordException e) {
      var errorDTO = ResponseError.conflict(e.getMessage());
      return ResponseEntity.status(errorDTO.status()).body(errorDTO);
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteAuthor(@PathVariable("id") String id) {
    try {
      var idAuthor = UUID.fromString(id);
      Optional<Author> authorOptional =  service.authorById(idAuthor);

      if (authorOptional.isEmpty()) {
        return ResponseEntity.notFound().build();
      }
      service.deleteAuthorId(authorOptional.get());
      return ResponseEntity.noContent().build();
    } catch (OperationNotPermittedException e) {
      var responseError = ResponseError.responseDefault(e.getMessage());
      return ResponseEntity.status(responseError.status()).body(responseError);
    }
  }
}
