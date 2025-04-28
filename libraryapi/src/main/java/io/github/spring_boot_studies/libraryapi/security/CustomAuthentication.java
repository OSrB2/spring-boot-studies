package io.github.spring_boot_studies.libraryapi.security;

import io.github.spring_boot_studies.libraryapi.model.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Getter
public class CustomAuthentication implements Authentication {

  private final User user;

  // O Spring Security usa o método getAuthorities() para verificar as permissões do usuário.
  // Ele retorna uma coleção de GrantedAuthority, que representa as permissões do usuário.
  // Nesse caso, estamos convertendo as roles do usuário em GrantedAuthority.
  @Override
  public Collection<GrantedAuthority> getAuthorities() {
    return this.user
        .getRoles() // Pega as roles do usuário
        .stream() // Cria um stream a partir da lista de roles
        .map(SimpleGrantedAuthority::new) // Converte cada role em um GrantedAuthority,
        // e o prefixo "Role_" é adicionado automaticamente pelo Spring Security ou pode ser adicionado aqui.
        // Ou pode ser removido no SecurityConfiguration com o método GrantedAuthorityDefaults
        .collect(Collectors.toList()); // Coleta os GrantedAuthority em uma lista
  }

  @Override
  public Object getCredentials() {
    return null;
  }

  @Override
  public Object getDetails() {
    return user; // Retorna o usuário como detalhes da autenticação
  }

  @Override
  public Object getPrincipal() {
    return user; // Retorna o usuário como principal da autenticação
  }

  @Override
  public boolean isAuthenticated() {
    return true; // Retorna true, indicando que a autenticação foi realizada com sucesso
  }

  @Override
  public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

  }

  @Override
  public String getName() {
    return user.getLogin(); // Retorna o nome do usuário
  }
}
