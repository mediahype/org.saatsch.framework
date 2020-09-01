package de.osrg.base.simplescript.swt;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.TreeItem;

import de.osrg.base.simplescript.Simplescript;

public class OnScriptSelectionChanged extends SelectionAdapter{

  private ScriptsUI client;

  public OnScriptSelectionChanged(ScriptsUI scriptsUI) {
    this.client = scriptsUI;
  }

  @Override
  public void widgetSelected(SelectionEvent e) {
    
    TreeItem treeItem = client.getTreeScripts().getSelection()[0];
    
    if (treeItem != null) {
      client.redrawScriptSteps((Simplescript) treeItem.getData());      
    }
    
    
  }
  
}
