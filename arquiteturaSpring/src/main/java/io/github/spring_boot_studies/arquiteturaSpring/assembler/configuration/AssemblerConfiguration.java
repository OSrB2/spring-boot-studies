package io.github.spring_boot_studies.arquiteturaSpring.assembler.configuration;

import io.github.spring_boot_studies.arquiteturaSpring.assembler.Engine;
import io.github.spring_boot_studies.arquiteturaSpring.assembler.EngineType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

// Essa classe serve para definir beans ou configurações específicas para o padrão Assembler.
// Por exemplo, se você tiver um bean de fábrica para criar carros, você pode configurá-lo aqui.

// Exemplo de um bean fictício:

/**
 * /@Bean
 * public CarFactory carFactory() {
 *  return new CarFactory();
 * }
 */

@Configuration
public class AssemblerConfiguration {

  @Bean(name = "aspiratedEngine")
  @Primary // Define um bean primário para ser injetado quando houver mais de um bean do mesmo tipo
  public Engine aspiratedEngine() {
    var engine = new Engine();
    engine.setHorsePower(120);
    engine.setCylinder(4);
    engine.setModel("XPTO-0");
    engine.setEngineCapacity(2.0);
    engine.setType(EngineType.ASPIRATED);
    return engine;
  }

  @Bean(name = "electricEngine")
  public Engine eletricEngine() {
    var engine = new Engine();
    engine.setHorsePower(110);
    engine.setCylinder(3);
    engine.setModel("TH-40");
    engine.setEngineCapacity(1.4);
    engine.setType(EngineType.ELECTRIC);
    return engine;
  }

  @Bean(name = "turboEngine")
  public Engine turboEngine() {
    var engine = new Engine();
    engine.setHorsePower(180);
    engine.setCylinder(4);
    engine.setModel("XPTO-01");
    engine.setEngineCapacity(1.5);
    engine.setType(EngineType.TURBO);
    return engine;
  }
}
