package io.github.spring.osrb2.rental_company.repository;

import io.github.spring.osrb2.rental_company.entity.CarEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class CarRepositoryTest {

  @Autowired
  CarRepository carRepository;

  CarEntity car;

  @BeforeEach
  void config() {
    car = new CarEntity();
    car.setModel("Sedan");
    car.setDailyValue(100.0);
    car.setCarYear(2024);
  }

  @Test
  @DisplayName("Should save a new car on database")
  void shouldSaveCar() {
    carRepository.save(car);

    assertNotNull(car.getId());
  }

  @Test
  @Sql("/sql/car-populate.sql")
  @DisplayName("Should find cars by model")
  void shouldFindCarByModel() {
    List<CarEntity> list = carRepository.findByModel("SUV");
    var car = list.stream().findFirst().get();

    assertEquals(1, list.size());
    assertThat(car.getModel().equals("SUV"));
  }

  @Test
  @DisplayName("Shoul find car by ID")
  void shouldFindCarById() {
    var savedCar = carRepository.save(car);
    Optional<CarEntity> findedCar = carRepository.findById(savedCar.getId());

    assertThat(findedCar).isPresent();
    assertThat(findedCar.get().getModel()).isEqualTo("Sedan");
  }

  @Test
  @DisplayName("Should update or edit car by ID")
  void shouldUpdateOrEditCarById() {
    var savedCar = carRepository.save(car);

    savedCar.setCarYear(2026);

    var updatedCar = carRepository.save(savedCar);

    assertThat(updatedCar.getCarYear()).isEqualTo(2026);
  }

  @Test
  @DisplayName("Should delete car by ID")
  void shouldDeleteCarById() {
    var savedCar = carRepository.save(car);

    carRepository.deleteById(savedCar.getId());

    Optional<CarEntity> findedCar = carRepository.findById(savedCar.getId());

    assertThat(findedCar).isEmpty();
  }
}