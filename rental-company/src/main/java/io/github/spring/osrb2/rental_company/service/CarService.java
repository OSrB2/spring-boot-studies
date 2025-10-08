package io.github.spring.osrb2.rental_company.service;

import io.github.spring.osrb2.rental_company.entity.CarEntity;
import io.github.spring.osrb2.rental_company.model.exception.EntityNotFoundException;
import io.github.spring.osrb2.rental_company.repository.CarRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {

  private final CarRepository carRepository;

  public CarService(CarRepository carRepository) {
    this.carRepository = carRepository;
  }

  public CarEntity save(CarEntity car) {
    if (car.getDailyValue() <= 0) {
      throw new IllegalArgumentException("The daily rate cannot be negative!");
    }
    return carRepository.save(car);
  }

  public List<CarEntity> listAll() {
    return carRepository.findAll();
  }

  public CarEntity findById(Long id) {
    return carRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Car is not found!"));
  }

  public CarEntity update(Long id, CarEntity carUpdated) {
    var carExists = carRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Car is not found!"));

    carExists.setCarYear(carUpdated.getCarYear());
    carExists.setModel(carUpdated.getModel());
    carExists.setDailyValue(carUpdated.getDailyValue());

    return carRepository.save(carExists);
  }

  public void delete(Long id) {
    var carExists = carRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Car is not found!"));

    carRepository.deleteById(id);
  }
}
