package de.osrg.base.simplescript.swt;

import java.util.Optional;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.osrg.base.simplescript.ScriptExecutor;
import de.osrg.base.simplescript.Simplescript;
import de.osrg.base.simplescript.Stepper;

public class CmdExecuteScript extends SelectionAdapter {

  private static final Logger LOG = LoggerFactory.getLogger(CmdExecuteScript.class);
  
  private ScriptsUI client;

  public CmdExecuteScript(ScriptsUI scriptsUI) {
    this.client = scriptsUI;
  }

  @Override
  public void widgetSelected(SelectionEvent e) {

    ScriptExecutor executor = new ScriptExecutor(new Stepper(client));
    Optional<Simplescript> selectedScript = client.getSelectedScript();
    selectedScript.ifPresent(script -> executor.exec(script) );
    
  }

}
