package org.saatsch.framework.base.simplescript;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimplescriptRuntime {

  private static final Logger LOG = LoggerFactory.getLogger(SimplescriptRuntime.class);
  
  /**
   * pointer to the current step in the script
   */
  private int currentStep;
  
  /**
   * the script that gets executed.
   */
  private Simplescript currentScript;
  
  private final IStepCallback client;
  
  public SimplescriptRuntime(IStepCallback client) {
    this.client = client;
  }
  
  /**
   * starts execution of the given {@link Simplescript}.
   * 
   * @param script
   * @throws ExecutionException 
   */
  public void execute(Simplescript script) throws ExecutionException {
    
    this.currentStep = 0;
    this.currentScript = script;
    client.start();
    executeStep(currentScript, 0);
    
    while (hasNext()) {
      next();
    }
    
  }

  public void next() throws ExecutionException {
    currentStep++;
    executeStep(currentScript, currentStep);
  }
  
  private boolean hasNext() {
    return currentScript.getSteps().size() > currentStep;
  }
  
  private void executeStep(Simplescript script, int step) throws ExecutionException {
    if (currentScript.getSteps().size() <= currentStep){
      LOG.info("last step. nothing to do.");
      client.stop();
      return;
    }
    
    SimpleStep s = currentScript.getSteps().get(step);
    client.step(s,step);
    
  }
  
  /**
   * stop execution with an error.
   * 
   * @param error
   */
  public void stop(String error) {
    currentStep = currentScript.getSteps().size();
    client.stop();
    LOG.error(error);
  }
  
}
