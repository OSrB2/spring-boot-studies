package io.github.spring_boot_studies.productsapi.controller;

import io.github.spring_boot_studies.productsapi.model.Product;
import io.github.spring_boot_studies.productsapi.repository.ProductRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/products")
public class ProductController {

  private ProductRepository productRepository;

  public ProductController(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @PostMapping
  public Product register(@RequestBody Product product) {
    var id = UUID.randomUUID().toString();
    product.setId(id);
    productRepository.save(product);
    System.out.println("Product registered: " + product);
    return product;
  }

  @GetMapping("/{id}")
  public Product findById(@PathVariable String id) {
    return productRepository.findById(id).orElse(null);
  }

  @PutMapping("/{id}")
  public Product update(@PathVariable String id, @RequestBody Product product) {
    product.setId(id);
    productRepository.save(product);
    return product;
  }

  @GetMapping
  public List<Product> find(@RequestParam("name") String name) {
    return productRepository.findByName(name);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable String id) {
    productRepository.deleteById(id);
  }
}
