package io.github.spring_boot_studies.libraryapi.config;

import io.github.spring_boot_studies.libraryapi.security.CustomUserDetailsService;
import io.github.spring_boot_studies.libraryapi.security.JwtCustomAuthenticationFilter;
import io.github.spring_boot_studies.libraryapi.security.SocialLoginSuccesHandler;
import io.github.spring_boot_studies.libraryapi.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

// Essa classe é responsável por configurar a segurança da aplicação.
// Ela pode incluir configurações de autenticação, autorização, CSRF, CORS, etc.

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true) // Habilita a segurança em métodos, permitindo o uso de anotações como @PreAuthorize
public class SecurityConfiguration {
  // Configuração padrão do Spring Security
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity,
                                                 SocialLoginSuccesHandler socialLoginSuccesHandler,
                                                 JwtCustomAuthenticationFilter jwtCustomAuthenticationFilter) throws Exception {
    return httpSecurity
        .csrf(AbstractHttpConfigurer::disable) // Desabilita a proteção CSRF)
        //.formLogin(Customizer.withDefaults()) // Habilita o login padrão do Spring Security
        .httpBasic(Customizer.withDefaults()) // Habilita a autenticação básica HTTP
        .formLogin(configurer -> {
          configurer.loginPage("/login"); // Define a página de login personalizada
        })
        .authorizeHttpRequests(authorize -> {

          // **** As regras de autorização definidas aqui, foram colocadas direto nos endpoint nos controllers ****
        authorize.requestMatchers("/login").permitAll(); // Qualquer usuário pode acessar
        authorize.requestMatchers(HttpMethod.POST, "/api/users").permitAll(); // Qualquer usuário pode fazer cadastro
        //  authorize.requestMatchers("/api/authors/**").hasRole("ADMIN"); // Usuários com ROLE de ADMIN conseguem acessar
        //  authorize.requestMatchers("/api/books/**").hasAnyRole("USER", "ADMIN"); // Usuários com ROLE de USER ou ADMIN conseguem acessar

          authorize.anyRequest().authenticated(); // Exige autenticação para todas as requisições
          // O anyReques() tem que ficar por último, ou ele irá anular todas as regras de acesso abaixo dele.
        })
        // Configuração do login com OAuth2 para login com o google
        .oauth2Login(oauth2 -> {
          oauth2
              .loginPage("/login") // Define a página de login personalizada com login social
              .successHandler(socialLoginSuccesHandler);
        })
        .oauth2ResourceServer(oauth2RS ->
            oauth2RS.jwt(Customizer.withDefaults())) // Configura o servidor de recursos OAuth2 para usar JWT como formato de token
        .addFilterAfter(jwtCustomAuthenticationFilter, BearerTokenAuthenticationFilter.class)
        .build();
  }

  // Define um bean do tipo PasswordEncoder que utiliza o algoritmo BCrypt para codificar senhas.
  // O parâmetro "10" representa o fator de força (work factor), que determina a complexidade do hash.
//  // Um fator maior aumenta a segurança, mas também o tempo de processamento.
//  @Bean
//  public PasswordEncoder passwordEncoder() {
//    return new BCryptPasswordEncoder(10);
//  }

  // Aqui serve para definir um UserDetailsService personalizado, se necessário
  // Pode usar um banco de dados, LDAP, ou qualquer outra fonte de dados para autenticação
  // O UserDetailsService é uma interface do Spring Security que carrega dados do usuário

  //@Bean -> UserDetailsService desabilitado para não conflitar com o UserDetailsService do Spring Security
  public UserDetailsService userDetailsService(UserService userService) {
    return new CustomUserDetailsService(userService);
  }

// *************** Cria dois usuários em memória para fins de teste ****************
//  @Bean
//  public UserDetailsService userDetailsService(PasswordEncoder encoder) {
//
//    UserDetails user1 = User.builder() // Cria um usuário com nome de usuário "user" e senha "123"
//        .username("user")
//        .password(encoder.encode("123"))
//        .roles("USER") // Define o papel do usuário como "USER"
//        .build(); // Constrói o objeto UserDetails
//
//    UserDetails user2 = User.builder()
//        .username("admin")
//        .password(encoder.encode("321"))
//        .roles("ADMIN") // Define o papel do usuário como "ADMIN"
//        .build();
//
//    // Cria um gerenciador de usuários em memória com dois usuários
//     return new InMemoryUserDetailsManager(user1, user2);
//  }

  // Configura o prefixo role
  @Bean
  public GrantedAuthorityDefaults grantedAuthorityDefaults() {
    return new GrantedAuthorityDefaults(""); // Isso remove o prefixo "ROLE_" das roles
  }

  // Configura no token jwt o prefixo scope
  // Essa configuração define um conversor de autenticação JWT personalizado
  @Bean
  public JwtAuthenticationConverter jwtAuthenticationConverter() {
    var authoritiesConverter = new JwtGrantedAuthoritiesConverter();
    authoritiesConverter.setAuthorityPrefix(""); // Remove o prefixo "ROLE_" das roles

    var converter = new JwtAuthenticationConverter();
    converter.setJwtGrantedAuthoritiesConverter(authoritiesConverter);

    return converter; // Retorna o conversor de autenticação JWT configurado
  }
}
