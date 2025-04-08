package io.github.spring_boot_studies.arquiteturaSpring.assembler.api;

import io.github.spring_boot_studies.arquiteturaSpring.assembler.CarStatus;
import io.github.spring_boot_studies.arquiteturaSpring.assembler.Engine;
import io.github.spring_boot_studies.arquiteturaSpring.assembler.HondaHRV;
import io.github.spring_boot_studies.arquiteturaSpring.assembler.Key;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/cars")
public class TestFactoryController {

  @Autowired
  @Turbo // anotação personalizada para injetar o motor aspirado @Aspirated, turbo @Turbo ou elétrico @Eletric.
  //@Qualifier("turboEngine")
  private Engine engine;

  @PostMapping
  public CarStatus startCar(@RequestBody Key key) {
    var car = new HondaHRV(engine);
    return car.ignite(key);
  }
}
