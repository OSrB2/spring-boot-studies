package io.github.spring_boot_studies.arquiteturaSpring.assembler;

import java.awt.*;

public class Car {
  private String model;
  private Color color;
  private Engine engine;
  private Assembler assembler;

  public Car(Engine engine) {
    this.engine = engine;
  }

  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }

  public Color getColor() {
    return color;
  }

  public void setColor(Color color) {
    this.color = color;
  }

  public Engine getEngine() {
    return engine;
  }

  public void setEngine(Engine engine) {
    this.engine = engine;
  }

  public Assembler getAssembler() {
    return assembler;
  }

  public void setAssembler(Assembler assembler) {
    this.assembler = assembler;
  }

  public CarStatus ignite(Key key) {
    if (key.getAssembler() != this.assembler) {
      return new CarStatus("Key not compatible with the car");
    } else {
      return new CarStatus("Engine on!" + engine);
    }
  }
}
