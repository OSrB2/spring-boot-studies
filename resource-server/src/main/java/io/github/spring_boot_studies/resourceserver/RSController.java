package io.github.spring_boot_studies.resourceserver;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RSController {

  @GetMapping("/public")
  public ResponseEntity<String> publicEndpoint() {
    return ResponseEntity.ok("Public endpoint OK!");
  }

  @GetMapping("/private")
  public ResponseEntity<String> privateEndpoint() {
    return ResponseEntity.ok("Private endpoint OK!");
  }
}
