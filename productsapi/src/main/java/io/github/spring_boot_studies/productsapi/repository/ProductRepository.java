package io.github.spring_boot_studies.productsapi.repository;

import io.github.spring_boot_studies.productsapi.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, String> {
  List<Product> findByName(String name);
}
