package org.saatsch.framework.jmmo.data.editor.ui.dialog;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.joda.beans.Bean;

import org.saatsch.framework.jmmo.cdi.container.JmmoContext;
import org.saatsch.framework.jmmo.data.api.AppIdSuggester;
import org.saatsch.framework.jmmo.data.api.DataConfig;
import org.saatsch.framework.jmmo.data.api.IntlStringService;
import org.saatsch.framework.jmmo.data.editor.ui.tabcontent.EditorTabContent;
import org.saatsch.framework.jmmo.data.DataSink;
import org.saatsch.framework.base.swt.DialogUtil;

/**
 * abstract base class for object context dialogs like e.g. "add object..", "modify object..."
 * 
 * @author saatsch
 *
 */
public abstract class AbstractObjectContextDialog extends Dialog {

  protected Shell shell;

  protected Class<? extends Bean> objectClass;
  protected Text txtName;
  protected Text txtId;
  protected Combo cmbSubClasses;
  protected final List<Class<?>> subclasses;
  protected Object ret;
  protected final EditorTabContent parentTab;

  protected DataSink data = JmmoContext.getBean(DataSink.class);
  protected AppIdSuggester suggester = JmmoContext.getBean(AppIdSuggester.class);

  protected IntlStringService stringService =
      JmmoContext.getBean(IntlStringService.class);
  
  public AbstractObjectContextDialog(EditorTabContent parent) {
    super(parent.getShell());
    this.objectClass = parent.getObjectClass();
    subclasses = JmmoContext.getBean(DataConfig.class).getSpecializationsFor(objectClass);
    this.parentTab = parent;

  }

  /**
   * Open the dialog.
   * 
   */
  public Object open() {
    createContents();
    shell.open();
    DialogUtil.center(shell, getParent());
    shell.layout();
    Display display = getParent().getDisplay();
    while (!shell.isDisposed()) {
      if (!display.readAndDispatch()) {
        display.sleep();
      }
    }
    return ret;
  }

  private void createContents() {
    shell = new Shell(getParent(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
    shell.setSize(386, 179);
    shell.setText(getTitle());

    Label lblName = new Label(shell, SWT.NONE);
    lblName.setBounds(10, 10, 49, 13);
    lblName.setText("Name:");

    Label lblId = new Label(shell, SWT.NONE);
    lblId.setBounds(10, 37, 49, 13);
    lblId.setText("ID:");

    txtName = new Text(shell, SWT.BORDER);

    txtName.setBounds(65, 10, 305, 19);

    txtId = new Text(shell, SWT.BORDER);
    txtId.setBounds(65, 37, 216, 19);

    Button cmdSuggest = new Button(shell, SWT.NONE);
    cmdSuggest.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        String suggest = suggester.suggest(txtName.getText(), objectClass);
        txtId.setText(suggest);
      }
    });

    cmdSuggest.setBounds(302, 34, 68, 22);
    cmdSuggest.setText("Suggest");

    Button cmdOk = new Button(shell, SWT.NONE);
    cmdOk.addSelectionListener(new SelectionAdapter() {

      @Override
      public void widgetSelected(SelectionEvent e) {
        okPressed();
      }

    });
    cmdOk.setBounds(302, 121, 68, 23);
    cmdOk.setText("OK");

    Label lblScb = new Label(shell, SWT.NONE);
    lblScb.setBounds(10, 72, 49, 13);
    lblScb.setText("Class:");

    cmbSubClasses = new Combo(shell, SWT.NONE);
    cmbSubClasses.setBounds(65, 69, 305, 21);


    for (Class<?> clazz : subclasses) {
      cmbSubClasses.add(clazz.getSimpleName());
    }

    // preselect first item if possible...
    if (cmbSubClasses.getItems().length > 0) {
      cmbSubClasses.select(0);
    }

    shell.setDefaultButton(cmdOk);

    postCreateContents();

  }

  protected abstract void postCreateContents();



  protected abstract String getTitle();

  protected abstract void okPressed();

}
