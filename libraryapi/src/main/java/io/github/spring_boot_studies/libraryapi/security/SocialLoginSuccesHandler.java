package io.github.spring_boot_studies.libraryapi.security;

import io.github.spring_boot_studies.libraryapi.model.User;
import io.github.spring_boot_studies.libraryapi.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

// Essa classe é responsável por lidar com o sucesso do login social
// Ela é chamada quando o usuário faz login com sucesso usando OAuth2 (Google, Facebook, etc.)
// Ela pega o usuário autenticado e o salva no contexto de segurança do Spring

@Component
@RequiredArgsConstructor
public class SocialLoginSuccesHandler extends SavedRequestAwareAuthenticationSuccessHandler {

  private static final String DEFAULT_PASSWORD = "123456"; // Senha padrão para usuários que não possuem senha

  private final UserService userService;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request,
                                      HttpServletResponse response,
                                      Authentication authentication) throws ServletException, IOException {

    OAuth2AuthenticationToken auth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
    OAuth2User oAuth2User = auth2AuthenticationToken.getPrincipal();

    String email = oAuth2User.getAttribute("email"); // Aqui você pode pegar o email do usuário autenticado

    User user = userService.getByEmail(email); // Aqui você pode pegar o usuário do banco de dados pelo email


    // Se o usuário não existir, você pode criar um novo usuário com o email e a senha padrão para o login social
    if (user == null) {
      user = registerUserDb(email);
    }

    authentication = new CustomAuthentication(user); // Aqui você cria um novo CustomAuthentication com o usuário autenticado

    SecurityContextHolder.getContext().setAuthentication(authentication); // Aqui você coloca o usuário autenticado no contexto de segurança do Spring

    super.onAuthenticationSuccess(request, response, authentication); // Aqui você chama o método pai para continuar o fluxo de autenticação

  }

  private User registerUserDb(String email) {
    User user;
    user = new User();
    user.setEmail(email);
    user.setLogin(getLoginFromEmail(email));
    user.setPassword(DEFAULT_PASSWORD);
    user.setRoles(List.of("ROLE_USER")); // Aqui você pode definir as roles do usuário
    userService.registerUser(user);
    return user;
  }

  // Esse método é responsável por pegar o login do usuário a partir do email
  private String getLoginFromEmail(String email) {
    return email.substring(0, email.indexOf("@"));
  }
}
