package io.github.spring_boot_studies.libraryapi.controller;

import io.github.spring_boot_studies.libraryapi.controller.dto.RegisterBookDTO;
import io.github.spring_boot_studies.libraryapi.controller.dto.ResponseResearchBookDTO;
import io.github.spring_boot_studies.libraryapi.controller.mappers.BookMapper;
import io.github.spring_boot_studies.libraryapi.model.Book;
import io.github.spring_boot_studies.libraryapi.model.BookGenre;
import io.github.spring_boot_studies.libraryapi.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController implements GenericController {

  private final BookService bookService;
  private final BookMapper bookMapper;

  @PostMapping
  public ResponseEntity<Void> registerBook(@RequestBody @Valid RegisterBookDTO bookDTO) {
    // Mapear o DTO para a entidade
    Book book = bookMapper.toEntity(bookDTO);
    // Enviar a entidade para o serviço validar e salvar na base
    bookService.registerBook(book);
    // Criar URL para acesso dos dados do livro
    var url = headerLocationGenerator(book.getId());
    // Retornar código created com header location
    return ResponseEntity.created(url).build();
  }

  @GetMapping("/{id}")
  public ResponseEntity<ResponseResearchBookDTO> findBookById(@PathVariable("id") String id) {
    return bookService.BookById(UUID.fromString(id))
        .map(book -> {
          var bookDTO = bookMapper.toDTO(book);
          return ResponseEntity.ok(bookDTO);
        }).orElseGet(() -> ResponseEntity.notFound().build());
  }
// Usando paginação
  @GetMapping
  public ResponseEntity<Page<ResponseResearchBookDTO>> searchWithFilter(
      @RequestParam(value = "isbn", required = false)
      String isbn,
      @RequestParam(value = "title", required = false)
      String title,
      @RequestParam(value = "author-name", required = false)
      String authorName,
      @RequestParam(value = "gender", required = false)
      BookGenre gender,
      @RequestParam(value = "publication-year", required = false)
      Integer publicationYear,
      @RequestParam(value = "page", defaultValue = "0")
      Integer page,
      @RequestParam(value = "page-size", defaultValue = "10")
      Integer pageSize
    ){

    Page<Book> resultPage = bookService.searchBookWithFilter(isbn, title, authorName, gender, publicationYear, page, pageSize);
    Page<ResponseResearchBookDTO> result = resultPage.map(bookMapper::toDTO);

    return ResponseEntity.ok(result);
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updateBook(@PathVariable("id") String id, @RequestBody @Valid RegisterBookDTO bookDTO) {
    return bookService.BookById(UUID.fromString(id))
        .map(book -> {
          Book bookEntity = bookMapper.toEntity(bookDTO);
          book.setPublicationDate(bookEntity.getPublicationDate());
          book.setIsbn(bookEntity.getIsbn());
          book.setPrice(bookEntity.getPrice());
          book.setGender(bookEntity.getGender());
          book.setTitle(bookEntity.getTitle());
          book.setAuthor(bookEntity.getAuthor());

          bookService.update(book);
          return ResponseEntity.noContent().build();
        }).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteBook(@PathVariable("id") String id) {
    return bookService.BookById(UUID.fromString(id))
        .map(book -> {
          bookService.deleteBook(book);
          return ResponseEntity.noContent().build();
        }).orElseGet(() -> ResponseEntity.notFound().build());
  }
}
