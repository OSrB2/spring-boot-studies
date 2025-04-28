package io.github.spring_boot_studies.libraryapi.controller;

import io.github.spring_boot_studies.libraryapi.security.CustomAuthentication;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginViewController {

  @GetMapping("/login")
  public String loginPage() {
    // Retorna o nome da view que será renderizada
    return "login"; // O Spring irá procurar por um arquivo chamado "login.html" na pasta de templates
  }

  @GetMapping("/")
  @ResponseBody
  public String homePage(Authentication authentication) {
    if (authentication instanceof  CustomAuthentication customAuthentication) {
      System.out.println(customAuthentication.getUser());
    }

    // Retorna o nome da view que será renderizada
    return "Olá " + authentication.getName(); // O Spring irá procurar por um arquivo chamado "home.html" na pasta de templates
  }
}


