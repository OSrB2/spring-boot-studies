package io.github.spring.osrb2.rental_company.controller;

import io.github.spring.osrb2.rental_company.entity.CarEntity;
import io.github.spring.osrb2.rental_company.model.exception.EntityNotFoundException;
import io.github.spring.osrb2.rental_company.service.CarService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cars")
public class CarController {

  private final CarService service;

  public CarController(CarService service) {
    this.service = service;
  }

  @PostMapping
  public ResponseEntity<Object> save(@RequestBody CarEntity car) {
    try {
      var savedCar = service.save(car);
      return ResponseEntity.status(HttpStatus.CREATED).body(savedCar);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getMessage());
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<CarEntity> findById(@PathVariable Long id) {
    try {
      var carFound = service.findById(id);
      return ResponseEntity.ok(carFound);
    } catch (EntityNotFoundException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @GetMapping
  public ResponseEntity<List<CarEntity>> allCars() {
    return ResponseEntity.ok(service.listAll());
  }

  @PutMapping("/{id}")
  public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody CarEntity carUpdate) {
    try {
      service.update(id, carUpdate);
      return ResponseEntity.noContent().build();
    } catch (EntityNotFoundException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    try {
      service.delete(id);
      return ResponseEntity.noContent().build();
    } catch (EntityNotFoundException e) {
      return ResponseEntity.notFound().build();
    }
  }
}
