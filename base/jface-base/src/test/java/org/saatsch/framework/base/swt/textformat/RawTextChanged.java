package org.saatsch.framework.base.swt.textformat;

import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;

public class RawTextChanged extends KeyAdapter{

  private TextFormatC client;

  public RawTextChanged(TextFormatC grammarEditor) {
    this.client = grammarEditor;
  }

  @Override
  public void keyReleased(KeyEvent e) {
    client.paintFormattedText();
  }

  
}
