package io.github.spring.osrb2.rental_company.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ClientTest {

  @Test
  @DisplayName("Shoul create a client with name")
  void shouldCreateClientWithName() {
    // 1. Cenário
    var client = new Client("Maria");

    // 2. Execução
    String name = client.getName();

    // 3. Verificação
    assertNotNull(name);
    assertTrue(name.startsWith("M"));
    assertNotEquals(100, name.length());
    assertEquals("Maria", name);
  }

  @Test
  @DisplayName("Should create a client without name")
  void shouldCreateClientWithoutName() {
    // 1. Cenário
    var client = new Client(null);

    // 2. Execução
    var name = client.getName();

    // 3. Verificação
    assertNull(name);
  }
}
