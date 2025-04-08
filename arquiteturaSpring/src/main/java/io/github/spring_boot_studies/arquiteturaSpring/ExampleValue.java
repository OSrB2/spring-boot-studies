package io.github.spring_boot_studies.arquiteturaSpring;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ExampleValue {

  // Utilizando a configuração personalizada criada no application.yml
  @Value("${app.config.variable}")
  private String variable;

  public void printVariable() {
    System.out.println("Valor da variável: " + variable);
  }


}
