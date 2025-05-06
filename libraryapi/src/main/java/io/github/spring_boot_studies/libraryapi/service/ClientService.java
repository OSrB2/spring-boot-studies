package io.github.spring_boot_studies.libraryapi.service;

import io.github.spring_boot_studies.libraryapi.model.Client;
import io.github.spring_boot_studies.libraryapi.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientService {

  private final ClientRepository clientRepository;
  private final PasswordEncoder passwordEncoder;

  public Client save(Client client) {
    var encodedPassword = passwordEncoder.encode(client.getClientSecret());
    client.setClientSecret(encodedPassword);
    return clientRepository.save(client);
  }

  public Client findById(String clientId) {
    return clientRepository.findByClientId(clientId);
  }
}
