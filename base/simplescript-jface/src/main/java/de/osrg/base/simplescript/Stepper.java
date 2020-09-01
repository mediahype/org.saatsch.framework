package de.osrg.base.simplescript;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 */
public class Stepper implements IStepCallback {

  private static final Logger LOG = LoggerFactory.getLogger(Stepper.class);
  private StepperCallback client;

  public Stepper(StepperCallback client) {
    this.client = client;
  }

  @Override
  public void step(SimpleStep simpleStep, int stepIndex) throws ExecutionException {
    LOG.info(">>>> Step: {}", simpleStep);
    
    client.markCurrentStep(stepIndex);
    if (simpleStep instanceof JfaceStep) {
      ((JfaceStep) simpleStep).execute();
    }

  }

  @Override
  public void start() {


  }

  @Override
  public void stop() {
    client.markCurrentStep(null);
  }


}
