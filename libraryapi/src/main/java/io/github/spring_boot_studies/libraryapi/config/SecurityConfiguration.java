package io.github.spring_boot_studies.libraryapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

// Essa classe é responsável por configurar a segurança da aplicação.
// Ela pode incluir configurações de autenticação, autorização, CSRF, CORS, etc.

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
  // Configuração padrão do Spring Security
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    return httpSecurity
        .csrf(AbstractHttpConfigurer::disable) // Desabilita a proteção CSRF)
        //.formLogin(Customizer.withDefaults()) // Habilita o login padrão do Spring Security
        .httpBasic(Customizer.withDefaults()) // Habilita a autenticação básica HTTP
        .formLogin(configurer -> {
          configurer.loginPage("/login") // Define a página de login personalizada
              .permitAll(); // Permite acesso de todos os usuários a página de login, mesmo não autenticados
        })
        .authorizeHttpRequests(authorize -> {
          authorize.anyRequest().authenticated(); // Exige autenticação para todas as requisições
        })
        .build();
  }
}
