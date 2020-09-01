package de.osrg.base.simplescript;

/**
 * must be implemented by simplescript clients. Required by {@link SimplescriptRuntime}.
 * 
 * 
 * @author saatsch
 *
 */
public interface IStepCallback {

  /**
   * tells this client to execute the given script step.
   * 
   * @param simpleStep the step to execute
   * @param stepIndex the index of the step in the script
   * @throws ExecutionException when an error occurred such that execution cannot continue.
   */
  void step(SimpleStep simpleStep, int stepIndex) throws ExecutionException;
  
  /**
   * signals that the next call to {@link #step(SimpleStep)} will be the first step in a new script.
   */
  void start();
  
  /**
   * called when the execution stopped.
   */
  void stop();
  
}
