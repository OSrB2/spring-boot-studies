package io.github.spring_boot_studies.libraryapi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginViewController {

  @GetMapping("/login")
  public String loginPage() {
    // Retorna o nome da view que será renderizada
    return "login"; // O Spring irá procurar por um arquivo chamado "login.html" na pasta de templates
  }
}


