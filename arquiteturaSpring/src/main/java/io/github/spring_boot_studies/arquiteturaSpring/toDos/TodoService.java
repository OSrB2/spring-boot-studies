package io.github.spring_boot_studies.arquiteturaSpring.toDos;

import org.springframework.stereotype.Service;

@Service
public class TodoService {

  private TodoRepository repository;
  private TodoValidator validator;
  private MailSender mailSender;

  public TodoService(TodoRepository todoRepository, TodoValidator todoValidator, MailSender mailSander) {
    // Injeção de dependência do repositório
    this.repository = todoRepository;
    this.validator = todoValidator;
    this.mailSender = mailSander;
  }

  public TodoEntity save(TodoEntity newTodo) {
    validator.validate(newTodo);
    return repository.save(newTodo);
  }

  public void updateStatus(TodoEntity todo) {
    repository.save(todo);
    String status = todo.getCompleted() == Boolean.TRUE ? "Concluido!" : "Não Concluido!";
    mailSender.send("ToDo " + todo.getDescription() + " foi atualizado para " + status);
  }

  public TodoEntity find(Integer id) {
    return repository.findById(id).orElseThrow(() -> new RuntimeException("ToDo not found"));
  }
}
