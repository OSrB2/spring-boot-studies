package io.github.spring_boot_studies.arquiteturaSpring;

import io.github.spring_boot_studies.arquiteturaSpring.toDos.TodoEntity;
import io.github.spring_boot_studies.arquiteturaSpring.toDos.TodoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Escopo padrão de um bean é singleton, ou seja, uma instância por aplicação.
 * / @Scope("prototype") Se o escopo for prototype, uma nova instância é criada a cada vez que o bean é solicitado.
 * / @Scope("request") Se o escopo for request, uma nova instância é criada a cada vez que uma requisição HTTP é feita.
 * / @Scope("session") Se o escopo for session, uma nova instância é criada a cada vez que uma sessão HTTP é criada.
 * / @Scope("application") Se o escopo for application, uma nova instância é criada a cada vez que a aplicação é iniciada.
 * E os existem constantes que podem ser usadas para definir o escopo do bean, como:
 * / @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE) Se o escopo for prototype, uma nova instância é criada a cada vez que o bean é solicitado.
 * / @Scope(ConfigurableBeanFactory.SCOPE_REQUEST) Se o escopo for request, uma nova instância é criada a cada vez que uma requisição HTTP é feita.
 * / @Scope(ConfigurableBeanFactory.SCOPE_SESSION) Se o escopo for session, uma nova instância é criada a cada vez que uma sessão HTTP é criada.
 * / @Scope(ConfigurableBeanFactory.SCOPE_APPLICATION) Se o escopo for application, uma nova instância é criada a cada vez que a aplicação é iniciada.
 * / @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) Se o escopo for singleton, uma nova instância é criada a cada vez que a aplicação é iniciada.
 * É interessante utilizar as constantes para evitar erros de digitação.
 */
@Lazy(false) // O @Lazy é usado para indicar que o bean deve ser criado apenas quando for necessário, ou seja, quando for solicitado. Mas o padrão é não utilizar.
@Component
@Scope("singleton") // É possível definir o escopo do bean, mas se nenhuma anotação for colocada, o padrão é singleton.
public class ManagedBean {
  // Não é interessante guardar o estado de um bean gerenciado pelo Spring, pois o Spring pode criar várias instâncias do mesmo bean.
  // O ideal é que o bean seja stateless, ou seja, não guarde estado.

  // Primeira forma de injeção
  @Autowired
  private TodoValidator validator;

//  @Autowired
//  private AppProperties properties; // Classe de configuração personalizada criada no application.yml

  // Segunda forma de injeção, o @Autowired pode ser usado no construtor mas não é necessário.
  public ManagedBean(TodoValidator validator) {
    this.validator = validator;
    // String variable = properties.getVariable(); // Acessando a variável do arquivo application.yml
  }

  public void doSomething() {
    var todo = new TodoEntity();
    validator.validate(todo);
  }

  // Terceira forma de injeção, menos comum, mas possível.
  @Autowired
  public void setValidator(TodoValidator validator) {
    this.validator = validator;
  }
}
