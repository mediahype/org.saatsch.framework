package org.saatsch.framework.base.simplescript;

import java.util.ArrayList;
import java.util.List;

public class StepTypeRegistry {

  private static final List<Class<? extends JfaceStep>> types = new ArrayList();

  
  public static void addType(Class<? extends JfaceStep> stepType) {
    types.add(stepType);
  }
  
  
  public static List<Class<? extends JfaceStep>> getTypes() {
    return types;
  }
  
}
