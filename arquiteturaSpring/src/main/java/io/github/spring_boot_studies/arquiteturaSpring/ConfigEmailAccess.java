package io.github.spring_boot_studies.arquiteturaSpring;

import io.github.spring_boot_studies.arquiteturaSpring.toDos.MailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigEmailAccess {

  @Autowired
  private AppProperties properties;

  @Bean
  public MailSender mailSender() {
    MailSender mailSender = new MailSender();
    // Configurações do MailSender
    return mailSender;
  }
}
