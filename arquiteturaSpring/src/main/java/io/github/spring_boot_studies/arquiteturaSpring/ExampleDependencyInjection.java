package io.github.spring_boot_studies.arquiteturaSpring;

import io.github.spring_boot_studies.arquiteturaSpring.toDos.*;
import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.Connection;

public class ExampleDependencyInjection {
  public static void main(String[] args) throws Exception {
    // Exemplo de injeção de dependência manual
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setUrl("url");
    dataSource.setUsername("user");
    dataSource.setPassword("password");

    Connection connection = dataSource.getConnection();

    EntityManager entityManager = null;

    TodoRepository repository = null;//new SimpleJpaRepository<TodoEntity, Integer>();
    TodoValidator todoValidator = new TodoValidator(repository);
    MailSender sender = new MailSender();

    TodoService todoService = new TodoService(repository, todoValidator, sender);

//    ManagedBean managedBean = new ManagedBean(null);
//    managedBean.setValidator(todoValidator);
//    if (condition == true) {
//      managedBean.doSomething();;
//    }
  }
}
