package org.saatsch.framework.base.simplescript;

import org.eclipse.swt.widgets.Shell;

/**
 * implementations give their own Logic
 */
public abstract class JfaceStep extends SimpleStep {

  public abstract OpenableEditor createEditDialog(Shell parent);
  
  public abstract void execute() throws ExecutionException;
  
}
