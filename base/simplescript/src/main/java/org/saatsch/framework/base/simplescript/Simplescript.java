package org.saatsch.framework.base.simplescript;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * model of a simple script.
 * 
 * @author saatsch
 *
 */
public class Simplescript {

  /**
   * the name of the script.
   */
  private String name = "New Script";

  /**
   * the steps in the script.
   */
  private List<SimpleStep> steps = new ArrayList<>();

  public List<SimpleStep> getSteps() {
    return steps;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  // -------------------------------------------------------------------------------

  private static final Logger LOG = LoggerFactory.getLogger(Simplescript.class);

  public static void addStepToScript(Simplescript script, SimpleStep step) {
    if (null == script || null == step) {
      return;
    }
    script.getSteps().add(step);
  }

  public static void removeStepFromScript(Simplescript script, SimpleStep step) {
    if (script == null || step == null) {
      return;
    }
    boolean remove = script.getSteps().remove(step);
    LOG.debug("removed: " + remove);
  }

  public static void moveStepUp(Simplescript script, SimpleStep step) {
    if (script == null || step == null) {
      return;
    }

    int indexOf = script.getSteps().indexOf(step);

    if (indexOf <= 0) { // cannot be moved up
      return;
    }

    script.getSteps().remove(step);
    script.getSteps().add(indexOf - 1, step);

  }

  public static void moveStepDown(Simplescript script, SimpleStep step) {
    if (script == null || step == null) {
      return;
    }

    int indexOf = script.getSteps().indexOf(step);

    if (indexOf < 0 || indexOf >= script.getSteps().size() - 1) { // cannot be moved down
      return;
    }

    script.getSteps().remove(step);
    script.getSteps().add(indexOf + 1, step);

  }


}
