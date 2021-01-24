package org.saatsch.framework.base.swt.textformat;

import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;

public class RawTextChanged extends KeyAdapter{

  private TextFormatC grammarEditor;

  public RawTextChanged(TextFormatC grammarEditor) {
    this.grammarEditor = grammarEditor;
  }

  @Override
  public void keyReleased(KeyEvent e) {
    grammarEditor.paintFormattedText();
  }

  
}
