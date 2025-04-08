package io.github.spring_boot_studies.arquiteturaSpring.assembler;

public class Key {
  private Assembler assembler;
  private String type;

  public Assembler getAssembler() {
    return assembler;
  }

  public void setAssembler(Assembler assembler) {
    this.assembler = assembler;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }
}
