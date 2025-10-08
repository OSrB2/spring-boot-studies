package io.github.spring.osrb2.rental_company.repository;

import io.github.spring.osrb2.rental_company.entity.CarEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarRepository extends JpaRepository<CarEntity, Long> {
  List<CarEntity> findByModel(String model);
}
