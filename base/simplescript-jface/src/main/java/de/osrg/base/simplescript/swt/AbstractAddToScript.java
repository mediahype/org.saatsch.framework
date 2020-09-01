package de.osrg.base.simplescript.swt;

import java.util.Optional;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import de.osrg.base.simplescript.SimpleStep;
import de.osrg.base.simplescript.Simplescript;


/**
 * 
 * 
 * @author saatsch,
 * @version
 */
public abstract class AbstractAddToScript extends SelectionAdapter {

  protected ScriptsUI client;

  public AbstractAddToScript(ScriptsUI scriptsUI) {
    this.client = scriptsUI;
  }

  @Override
  public void widgetSelected(SelectionEvent e) {

    Optional<Simplescript> script = getSelectedScript();
    
    if (!script.isPresent()) {
      return;
    }

    SimpleStep addToScript = showDialog();

    if (null != addToScript) {
      Simplescript.addStepToScript(script.get(), addToScript);
      client.redrawScriptSteps(script.get());
    }


  }

  protected Optional<Simplescript> getSelectedScript() {
    return client.getSelectedScript();
  }


  /**
   * called when the user wants to add a step to a script.
   * 
   * @return a {@link SimpleStep} or <code>null</code> if the user cacelled the dialog.
   */
  protected abstract SimpleStep showDialog();

}
