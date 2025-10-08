package io.github.spring.osrb2.rental_company.service;

import io.github.spring.osrb2.rental_company.entity.CarEntity;
import io.github.spring.osrb2.rental_company.repository.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CarServiceTest {
  @InjectMocks
  CarService service;

  @Mock
  CarRepository repository;

  CarEntity car;

  @BeforeEach
  void config() {
    car = new CarEntity();
    car.setModel("Sedan");
    car.setDailyValue(50.0);
    car.setCarYear(2026);
  }

  @Test
  @DisplayName("Should save a new car")
  void shouldSaveNewCar() {
    repository.save(car);
  }

  @Test
  @DisplayName("Should list all cars")
  void shouldListAllCars() {
  }

  @Test
  @DisplayName("Should find car by ID")
  void shouldFindCarById() {
  }

  @Test
  @DisplayName("Should update or edit car by iD")
  void shouldUpdateOrEditCar() {
  }

  @Test
  @DisplayName("Should delete car by ID")
  void shouldDeleteCar() {
  }
}