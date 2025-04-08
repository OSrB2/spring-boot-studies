package io.github.spring_boot_studies.arquiteturaSpring.toDos;

import org.springframework.stereotype.Component;

// @Component é uma annotação indica que a classe é um componente do Spring,
// permitindo que o Spring a detecte e registre automaticamente durante a varredura de componentes.
 @Component
 public class TodoValidator {

   private TodoRepository todoRepository;

    public TodoValidator(TodoRepository todoRepository) {
      // Injeção de dependência do repositório
      this.todoRepository = todoRepository;
    }

   public void validate(TodoEntity todo) {
     if (existsTodoWithDescription(todo.getDescription())) {
       throw new IllegalArgumentException("Já existe um ToDo com essa descrição");
     }
   }

   private boolean existsTodoWithDescription(String description) {
      return todoRepository.existsByDescription(description);
   }
}
