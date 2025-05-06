package io.github.spring_boot_studies.libraryapi.controller;

import io.github.spring_boot_studies.libraryapi.model.Client;
import io.github.spring_boot_studies.libraryapi.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController {

  private final ClientService clientService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @PreAuthorize("hasRole('ADMIN')")
  public void saveClient(@RequestBody Client client) {
    clientService.save(client);
  }
}
