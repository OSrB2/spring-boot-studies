package io.github.spring_boot_studies.libraryapi.security;

import io.github.spring_boot_studies.libraryapi.model.User;
import io.github.spring_boot_studies.libraryapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityService {

  private final UserService userService;

  // Aqui você pode implementar a lógica para obter o usuário logado
  // Isso pode variar dependendo de como você está gerenciando a autenticação
  public User getLoggedUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication instanceof CustomAuthentication customAuthentication) {
      return customAuthentication.getUser();
    }

    return null;
  }
}
