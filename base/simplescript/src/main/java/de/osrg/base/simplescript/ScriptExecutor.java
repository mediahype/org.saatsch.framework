package de.osrg.base.simplescript;

import de.osrg.base.simplescript.ExecutionException;
import de.osrg.base.simplescript.Simplescript;
import de.osrg.base.simplescript.SimplescriptRuntime;

public class ScriptExecutor {

  private SimplescriptRuntime runtime;
 
  public ScriptExecutor(IStepCallback stepper) {
    this.runtime = new SimplescriptRuntime(stepper);
  }
  
  
  public void exec(Simplescript script) {
    new Thread(()-> {
      try {
        runtime.execute(script);
      } catch (ExecutionException e) {
        runtime.stop(e.getMessage());
      }
    }, "scriptThread").start();
  }
  
}
