package io.github.spring.osrb2.rental_company.service;

import io.github.spring.osrb2.rental_company.entity.CarEntity;
import io.github.spring.osrb2.rental_company.model.exception.EntityNotFoundException;
import io.github.spring.osrb2.rental_company.repository.CarRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarServiceTest {
  @InjectMocks
  CarService service;

  @Mock
  CarRepository repository;

  CarEntity car;
  CarEntity carExist;
  CarEntity carUpdate;

  @BeforeEach
  void config() {
    car = new CarEntity();
    car.setModel("Sedan");
    car.setDailyValue(50.0);
    car.setCarYear(2026);

    carExist = new CarEntity();
    carExist.setId(1L);
    carExist.setModel("Sedan");
    carExist.setDailyValue(50.0);
    carExist.setCarYear(2026);

    carUpdate = new CarEntity();
    carUpdate.setModel("Hatch");
    carUpdate.setDailyValue(50.0);
    carUpdate.setCarYear(2025);
  }

  @Test
  @DisplayName("Should save a new car")
  void shouldSaveNewCar() {
    when(repository.save(any(CarEntity.class))).thenReturn(car);

    var carSaved = service.save(car);
    assertNotNull(carSaved);
    assertEquals("Sedan", carSaved.getModel());
    verify(repository).save(any());
  }

  @Test
  @DisplayName("Should return an exception when trying to save a car with a negative daily rate ")
  void shouldReturnExceptionIfNegativeDaily() {
    car.setDailyValue(0);
    var error = Assertions.catchThrowable(() -> service.save(car));

    assertThat(error).isInstanceOf(IllegalArgumentException.class);

    verify(repository, never()).save(any());

  }

  @Test
  @DisplayName("Should list all cars")
  void shouldListAllCars() {
    var carList = List.of(car);
    when(repository.findAll()).thenReturn(carList);

    List<CarEntity> result = service.listAll();

    assertThat(result).hasSize(1);
    verify(repository, times(1)).findAll();
  }

  @Test
  @DisplayName("Should find car by ID")
  void shouldFindCarById() {
    when(repository.findById(carExist.getId())).thenReturn(Optional.of(carExist));

    var carFounded = service.findById(carExist.getId());

    assertThat(carFounded.getModel()).isEqualTo("Sedan");
  }

  @Test
  @DisplayName("Should update or edit car by iD")
  void shouldUpdateOrEditCar() {
    when(repository.findById(1L)).thenReturn(Optional.of(carExist));
    when(repository.save(any())).thenReturn(carUpdate);

    var result = service.update(1L, carUpdate);

    assertEquals(2025, result.getCarYear());
    verify(repository, times(1)).save(any());
  }

  @Test
  @DisplayName("Should return an exception when trying to update a car that does not exist.")
  void shouldReturnExpectionIfCarNotFound() {

    when(repository.findById(any())).thenReturn(Optional.empty());

    var error = catchThrowable(() -> service.update(carExist.getId(), car));

    assertThat(error).isInstanceOf(EntityNotFoundException.class);
    verify(repository, never()).save(any());
  }

  @Test
  @DisplayName("Should delete car by ID")
  void shouldDeleteCar() {
    when(repository.findById(any())).thenReturn(Optional.of(carExist));
    service.delete(carExist.getId());

    verify(repository, times(1)).deleteById(carExist.getId());
  }

  @Test
  @DisplayName("Should return an exception when trying to delete a car that does not exist.")
  void shouldDReturnExceptionIfTryDeleteCarNoFound() {
    when(repository.findById(any())).thenReturn(Optional.empty());

    var error = catchThrowable(() -> service.delete(carExist.getId()));

    assertThat(error).isInstanceOf(EntityNotFoundException.class);
    verify(repository, never()).deleteById(any());
  }
}