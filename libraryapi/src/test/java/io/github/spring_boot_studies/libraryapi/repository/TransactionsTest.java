package io.github.spring_boot_studies.libraryapi.repository;

import io.github.spring_boot_studies.libraryapi.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class TransactionsTest {
  // Estudo de transações
  @Autowired
  AuthorRepository authorRepository;

  @Autowired
  TransactionService transactionService;

  /**
   * Commit -> salva as alterações no banco de dados
   * Rollback -> desfaz as alterações no banco de dados
   */
  @Test
  void simpleTransactionTest() {
    transactionService.execute();
  }

  @Test
  void transactionStateManagedTest() {
    transactionService.updateWithoutUpdate();
  }
}
