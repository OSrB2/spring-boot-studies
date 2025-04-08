package io.github.spring_boot_studies.arquiteturaSpring.toDos;

import org.springframework.stereotype.Component;

@Component
public class MailSender {
  public void send(String message) {
    System.out.println("Email enviado com a mensagem: " + message);
  }
}
