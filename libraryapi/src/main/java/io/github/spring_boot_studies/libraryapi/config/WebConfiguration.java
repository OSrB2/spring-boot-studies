package io.github.spring_boot_studies.libraryapi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// Essa classe é responsável por configurar o Spring MVC.
// Ela pode incluir configurações de view resolvers, interceptors, etc.
// WebMvcConfigurer é uma interface que permite personalizar a configuração do Spring MVC.

@Configuration
@EnableWebMvc // Habilita o suporte ao Spring MVC para utilizar páginas web
public class WebConfiguration implements WebMvcConfigurer {

  @Override // Esse método
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addViewController("/login").setViewName("login"); // Mapeia a URL "/login" para a view "login"
    registry.setOrder(Ordered.HIGHEST_PRECEDENCE); // Define a ordem de prioridade para o controlador de visualização
  }
}
