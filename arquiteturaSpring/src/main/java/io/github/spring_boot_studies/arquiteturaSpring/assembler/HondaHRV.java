package io.github.spring_boot_studies.arquiteturaSpring.assembler;

import java.awt.*;

public class HondaHRV extends Car{

  public HondaHRV(Engine engine) {
    super(engine);
    setModel("HRV");
    setColor(Color.black);
    setAssembler(Assembler.HONDA);
  }
}
