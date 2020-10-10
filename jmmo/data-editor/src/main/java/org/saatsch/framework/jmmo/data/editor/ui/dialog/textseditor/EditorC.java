package org.saatsch.framework.jmmo.data.editor.ui.dialog.textseditor;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.saatsch.framework.jmmo.cdi.container.JmmoContext;
import org.saatsch.framework.jmmo.cdi.container.Lazy;
import org.saatsch.framework.jmmo.data.DataSink;
import org.saatsch.framework.jmmo.data.api.DataConfig;
import org.saatsch.framework.jmmo.data.api.model.IntlString;

/**
 * composite that edits one {@link IntlString}.
 * 
 * @author saatsch
 *
 */
public class EditorC extends Composite {

  private IntlString currentString;

  private DataSink data = JmmoContext.getBean(DataSink.class);
  
  private Lazy<DataConfig> cfg =  Lazy.of(() -> JmmoContext.getBean(DataConfig.class));

  private Text txtEntry;

  private IntlStringsDialog client;
  
  
  public EditorC(Composite parent, IntlStringsDialog client) {
    super(parent, SWT.NONE);
    this.client = client;
    setLayout(new GridLayout(1, false));

    Label lblTextEntry = new Label(this, SWT.NONE);
    lblTextEntry.setText("Text Entry:");

    txtEntry = new Text(this, SWT.BORDER | SWT.MULTI);
    GridData gd_txtEntry = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
    gd_txtEntry.heightHint = 193;
    txtEntry.setLayoutData(gd_txtEntry);
    
    Button cmdSaveEntry = new Button(this, SWT.NONE);
    cmdSaveEntry.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        currentString.setForLanguage(cfg.get().getCurrentLanguage(), txtEntry.getText());
        data.save(currentString);
        client.refreshTree();
      }
    });
    cmdSaveEntry.setText("Save");
    
  }

  
  void setTextToEdit(IntlString intlString) {
    this.currentString = intlString;
    updateText();
    
  }
  
  private void updateText() {
    if (null != currentString) {
      txtEntry.setText(currentString.getForLanguage(cfg.get().getCurrentLanguage()));      
    }
  }


  void languageChanged() {
    updateText();
  }
  
}
