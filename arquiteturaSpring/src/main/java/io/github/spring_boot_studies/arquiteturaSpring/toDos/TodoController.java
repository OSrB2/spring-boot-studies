package io.github.spring_boot_studies.arquiteturaSpring.toDos;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("api/todos")
public class TodoController {
  private TodoService service;

  private TodoController(TodoService todoService) {
    // Injeção de dependência do serviço
    this.service = todoService;
  }

  @PostMapping
  public TodoEntity register(@RequestBody TodoEntity newTodo)  {
    try {
      return this.service.save(newTodo);
    } catch (IllegalArgumentException e) {
      var messageError = e.getMessage();
      throw new ResponseStatusException(HttpStatus.CONFLICT, messageError);
    }
  }

  @PutMapping("/{id}")
  public void updateStatus(@PathVariable("id") Integer id, @RequestBody TodoEntity todo) {
    todo.setId(id);
    service.updateStatus(todo);
  }

  @GetMapping("/{id}")
  public TodoEntity find(@PathVariable("id") Integer id) {
    return service.find(id);
  }
}
