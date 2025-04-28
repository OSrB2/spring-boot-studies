package io.github.spring_boot_studies.libraryapi.security;

import io.github.spring_boot_studies.libraryapi.model.User;
import io.github.spring_boot_studies.libraryapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final UserService userService;

  // Método que carrega o usuário pelo login
  @Override
  public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
    User user = userService.getByLogin(login);

    if (user == null) {
      throw new UsernameNotFoundException("User not found");
    }

    // Usando o caminho padrão do Spring Security para criar o UserDetails, para evitar o conflito com a classe User.
    // Esse UserDetails é o que o Spring Security usa para autenticar o usuário.
    return org.springframework.security.core.userdetails.User.builder()
        .username(user.getLogin())
        .password(user.getPassword())
        .roles(user.getRoles().toArray(new String[user.getRoles().size()]))
        .build();
  }
}
