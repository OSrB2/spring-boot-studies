package io.github.spring_boot_studies.libraryapi.controller;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

public interface GenericController {
  default URI headerLocationGenerator(UUID id) { // default é a forma de criar métodos com implementação dentro de uma interface
    return ServletUriComponentsBuilder.fromCurrentRequest() // Pega a URI atual, adiciona o id do autor e retorna a URI
        .path("/{id}")
        .buildAndExpand(id)
        .toUri();
  }
}
