package io.github.spring_boot_studies.libraryapi.security;

import io.github.spring_boot_studies.libraryapi.model.User;
import io.github.spring_boot_studies.libraryapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

  private final UserService userService;
  private final PasswordEncoder passwordEncoder;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    // Aqui estamos pegando o login e a senha que foram digitados pelo usuário
    String login = authentication.getName();
    String passwordToCheck = authentication.getCredentials().toString();

    // Aqui estamos pegando o usuário do banco de dados pelo login

    User findedUser = userService.getByLogin(login);

    if (findedUser == null) {
      throw getUserNotFoundError();
    }

    // Aqui estamos verificando se a senha que foi digitada pelo usuário é igual a senha que está no banco de dados
    String encriptedPassword = findedUser.getPassword();

    boolean passwordMatch = passwordEncoder.matches(passwordToCheck, encriptedPassword);

    // Se a senha for igual, então o usuário está autenticado
    if (passwordMatch) {
      return new CustomAuthentication(findedUser);
    }

    // Se a senha não for igual, então o usuário não está autenticado
    throw getUserNotFoundError();
  }

  private UsernameNotFoundException getUserNotFoundError() {
    return new UsernameNotFoundException("User and/or password is invalid");
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return authentication.isAssignableFrom(UsernamePasswordAuthenticationToken.class);
  }
}
