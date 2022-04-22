package org.saatsch.framework.base.jfxbase.demo;

import org.junit.Test;
import org.saatsch.framework.base.jfxbase.control.EnumCombo;

import javafx.application.Application;
import org.saatsch.framework.base.jfxbase.control.VBox;

public class EnumComboDemo extends AbstractDemo {

  @Override
  protected void fill(VBox root) {
    root.getChildren().add(new EnumCombo(DemoEnum.class));
    
  }

  @Test
  public void test() {
    Application.launch(this.getClass());
  }
  
}
