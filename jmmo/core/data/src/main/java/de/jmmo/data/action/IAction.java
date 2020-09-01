package de.jmmo.data.action;

/**
 * represents an action. This interface is introduced to group Actions on data and to later be able to
 * implement undo functionality.
 * 
 * @author saatsch
 *
 */
public interface IAction {

  /**
   * execute the action and return a return value. What the return value represents - or if it can be 
   * <code>null</code> depends on the implementation.
   * 
   * @return an arbitrary return value. Can be <code>null</code>
   */
  public Object execute();

}
