package io.github.spring_boot_studies.arquiteturaSpring;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration // Classe de configuração do Spring
@ConfigurationProperties(prefix = "app.config") // Prefixo para as propriedades do arquivo application.yml
public class AppProperties {
  private String variable;
  private Integer value1;

  public String getVariable() {
    return variable;
  }

  public void setVariable(String variable) {
    this.variable = variable;
  }

  public Integer getValue1() {
    return value1;
  }

  public void setValue1(Integer value1) {
    this.value1 = value1;
  }
}
