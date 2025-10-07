package io.github.spring.osrb2.rental_company.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CarTest {

  @Test
  @DisplayName("Should calculate the rental correct value")
  void shouldCalculateRentalValue() {
    // 1. Cenário
    Car car = new Car("Sedan", 100.0);

    // 2. Execução
    double total = car.calculateRentValue(3);

    // 3. Veficação
    assertEquals(300.0, total);
  }

  @Test
  @DisplayName("Should calculate the rental correct value with amount")
  void shouldCalculateRentalValueAmount() {
    // 1. Cenário
    Car car = new Car("Sedan", 100.0);
    int numberDays = 6;

    // 2. Execução
    double total = car.calculateRentValue(numberDays);

    // 3. Veficação
    assertEquals(550.0, total);
  }
}
