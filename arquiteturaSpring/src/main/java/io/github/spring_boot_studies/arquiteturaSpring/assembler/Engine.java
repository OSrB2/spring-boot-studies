package io.github.spring_boot_studies.arquiteturaSpring.assembler;

public class Engine {
  private String model;
  private Integer horsePower;
  private Integer cylinder;
  private Double engineCapacity;
  private EngineType type;

  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }

  public Integer getHorsePower() {
    return horsePower;
  }

  public void setHorsePower(Integer horsePower) {
    this.horsePower = horsePower;
  }

  public Integer getCylinder() {
    return cylinder;
  }

  public void setCylinder(Integer cylinder) {
    this.cylinder = cylinder;
  }

  public Double getEngineCapacity() {
    return engineCapacity;
  }

  public void setEngineCapacity(Double engineCapacity) {
    this.engineCapacity = engineCapacity;
  }

  public EngineType getType() {
    return type;
  }

  public void setType(EngineType type) {
    this.type = type;
  }

  @Override
  public String toString() {
    return "Engine{" +
        "model='" + model + '\'' +
        ", horsePower=" + horsePower +
        ", cylinder=" + cylinder +
        ", engineCapacity=" + engineCapacity +
        ", type=" + type +
        '}';
  }
}
