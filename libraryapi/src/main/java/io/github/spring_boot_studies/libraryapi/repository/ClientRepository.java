package io.github.spring_boot_studies.libraryapi.repository;

import io.github.spring_boot_studies.libraryapi.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClientRepository extends JpaRepository<Client, UUID> {

  Client findByClientId(String clientId);
}
