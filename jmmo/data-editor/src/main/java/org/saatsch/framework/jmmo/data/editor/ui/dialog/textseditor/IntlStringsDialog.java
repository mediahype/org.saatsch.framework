package org.saatsch.framework.jmmo.data.editor.ui.dialog.textseditor;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.saatsch.framework.base.swt.SwtUtil;
import org.saatsch.framework.jmmo.cdi.container.JmmoContext;
import org.saatsch.framework.jmmo.cdi.container.Lazy;
import org.saatsch.framework.jmmo.data.api.DataConfig;
import org.saatsch.framework.jmmo.data.api.IntlStringService;
import org.saatsch.framework.jmmo.data.api.model.IntlString;

/**
 * the editing of {@link IntlString}s gets its own dialog window.
 * 
 * @author saatsch
 *
 */
public class IntlStringsDialog extends Dialog {

  private Lazy<DataConfig> cfg =  Lazy.of(() -> JmmoContext.getBean(DataConfig.class));
  
  private Shell shell;
  private Text txtSearch;

  private TableViewer tvText;

  private EditorC editorC;

  

  public IntlStringsDialog(Shell parent) {
    super(parent, SWT.CLOSE | SWT.RESIZE | SWT.PRIMARY_MODAL);
  }

  public void open() {
    createContents();
    shell.open();
    shell.layout();
    Display display = getParent().getDisplay();
    while (!shell.isDisposed()) {
      if (!display.readAndDispatch()) {
        display.sleep();
      }
    }
    return;

  }

  private void createContents() {
    shell = new Shell(getParent(), getStyle());
    shell.setSize(1034, 627);
    shell.setText("Text");
    shell.setLayout(new GridLayout(1, false));

    SashForm sashForm = new SashForm(shell, SWT.NONE);
    sashForm.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

    Composite cmpLeft = new Composite(sashForm, SWT.NONE);
    cmpLeft.setLayout(new GridLayout(2, false));

    Label lblLanguage = new Label(cmpLeft, SWT.NONE);
    lblLanguage.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
    lblLanguage.setText("Language");

    Combo cmbLanguage = new Combo(cmpLeft, SWT.NONE);
    cmbLanguage.addListener(SWT.Selection, e -> {
      cfg.get().setCurrentLanguage(cmbLanguage.getItem(cmbLanguage.getSelectionIndex()));
      refreshTable();
      editorC.languageChanged();

    });
    cmbLanguage.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
    cmbLanguage.add("de");
    cmbLanguage.add("en");
    SwtUtil.selectInCombo(cmbLanguage, cfg.get().getCurrentLanguage());

    Label lblSearch = new Label(cmpLeft, SWT.NONE);
    lblSearch.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
    lblSearch.setText("Search:");

    txtSearch = new Text(cmpLeft, SWT.BORDER);
    txtSearch.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

    tvText = new TableViewer(cmpLeft, SWT.BORDER | SWT.FULL_SELECTION);
    Table tblText = tvText.getTable();
    tblText.addSelectionListener(new IntlStringsTableSelected(this));
    tblText.setHeaderVisible(true);
    tblText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));

    TableViewerColumn colViewerText = new TableViewerColumn(tvText, SWT.NONE);
    TableColumn colText = colViewerText.getColumn();
    colText.setWidth(324);
    colText.setText("Text");

    TableViewerColumn colViewerId = new TableViewerColumn(tvText, SWT.NONE);
    TableColumn colId = colViewerId.getColumn();
    colId.setWidth(226);
    colId.setText("ID");

    TableViewerColumn colViewerXlat = new TableViewerColumn(tvText, SWT.NONE);
    TableColumn colXlat = colViewerXlat.getColumn();
    colXlat.setWidth(100);
    colXlat.setText("Translations");

    
    
    new Label(cmpLeft, SWT.NONE);
    new Label(cmpLeft, SWT.NONE);

    editorC = new EditorC(sashForm, this);

    
    
    sashForm.setWeights(new int[] {590, 423});



    fillContents();


  }

  private void fillContents() {
    tvText.setContentProvider(ArrayContentProvider.getInstance());
    tvText.setLabelProvider(new Labels());
    tvText.setInput(JmmoContext.getBean(IntlStringService.class).loadAll());

  }

  void setTextToEdit(IntlString data) {
    editorC.setTextToEdit(data);
  }

  void refreshTable() {
    tvText.refresh();
  }

}
