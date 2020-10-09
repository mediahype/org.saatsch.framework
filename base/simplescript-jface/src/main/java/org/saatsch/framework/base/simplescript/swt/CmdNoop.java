package org.saatsch.framework.base.simplescript.swt;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CmdNoop extends SelectionAdapter {

  private static final Logger LOG = LoggerFactory.getLogger(CmdNoop.class);
  
  private ScriptsUI client;

  public CmdNoop(ScriptsUI scriptsUI) {
    this.client = scriptsUI;
  }

  @Override
  public void widgetSelected(SelectionEvent e) {
    
  }


}
