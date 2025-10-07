package io.github.spring.osrb2.rental_company;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseTest {

  static Connection connection;

  @BeforeAll // -> IMPORTANTE!! @BeforeAll deve ser colocado em um método "static" obrigatoriamente. E antes dos testes
  static void setUpDataBase() throws SQLException {
    connection = DriverManager.getConnection("jdbc:h2:mem:testdb", "sa", "");
    connection.createStatement().execute("CREATE TABLE users (id INT, name VARCHAR)");
  }

  @Test
  @DisplayName("Should insert user in table users")
  void insertUserTest() throws Exception {
    connection.createStatement().execute("insert into users(id, name) values (1, 'pedro')");
  }

  @AfterAll // -> IMPORTANTE!! @AfterAll deve ser colocado em um método "static" obrigatoriamente. E após todos os testes.
  static void closeDataBase() throws Exception {
    connection.close();
  }
}
