package org.saatsch.framework.base.simplescript;

/**
 * model of a step in a {@link Simplescript}.
 * 
 * @author saatsch
 *
 */
public abstract class SimpleStep {

  /**
   * the name of this step.
   */
  private String name;

  /**
   * @return the name of this step.
   */
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  /**
   * @return provide more detailed info.
   */
  public abstract String getMoreInfo();
  
}
