package io.github.spring_boot_studies.libraryapi.service;

import io.github.spring_boot_studies.libraryapi.model.Author;
import io.github.spring_boot_studies.libraryapi.model.Book;
import io.github.spring_boot_studies.libraryapi.model.BookGenre;
import io.github.spring_boot_studies.libraryapi.repository.AuthorRepository;
import io.github.spring_boot_studies.libraryapi.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class TransactionService {

  @Autowired
  private AuthorRepository authorRepository;
  @Autowired
  private BookRepository bookRepository;

  /// livro (titulo,...., nome_arquivo) -> id.png
  @Transactional
  public void registerBookWithImage() {
    /**
     * sava o livro
     * repository.save(livro)
     *
     * pega o id do livro = livro.getId()
     * var id = livro.getId();
     *
     *  salvar foto do livro -> bucket núvem
     *  bucketService.salvar(livro.getImagem(), id + ".png");
     *
     *  atualizar o nome do arquivo salvo
     *  livro.setNomeArquivoImagem(id + ".png");
     *  repository.save(livro);
     */
  }

  @Transactional
  public void updateWithoutUpdate() {
    var book = bookRepository.findById(UUID.fromString("38f3ebef-52a0-4729-a697-18f56585bfb8")).orElse(null);

    book.setTitle("O Guia do Mochileiro das Galáxias");

  }

  @Transactional // só funciona em métodos public
  public void execute() {
    // Salva o autor
    Author author = new Author();
    author.setName("Isaac Asimov");
    author.setDateBirth(LocalDate.of(1920, 1, 2));
    author.setNationality("American");

    authorRepository.saveAndFlush(author);

    // Salva o livro
    Book book = new Book();
    book.setTitle("Foundation");
    book.setAuthor(author);
    book.setPublicationDate(LocalDate.of(1951, 11, 1));
    book.setIsbn("978-0-553-80371-0");
    book.setPrice(BigDecimal.valueOf(320.80));
    book.setGender(BookGenre.FICTION);

    bookRepository.saveAndFlush(book); // saveAndFlush faz o flush no banco de dados, ou seja, salva as alterações imediatamente e não espera o commit da transação

    if (author.getName().equals("Isaac Asimov")) {
      throw new RuntimeException("Rollback!");
    }
  }
}
