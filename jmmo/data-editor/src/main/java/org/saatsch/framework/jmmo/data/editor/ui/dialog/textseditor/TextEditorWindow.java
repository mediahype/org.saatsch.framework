package org.saatsch.framework.jmmo.data.editor.ui.dialog.textseditor;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.CoolBar;
import org.eclipse.swt.widgets.CoolItem;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;

import org.saatsch.framework.jmmo.cdi.container.JmmoContext;
import org.saatsch.framework.jmmo.data.api.IntlStringService;

public class TextEditorWindow extends Dialog {
  private Shell shell;
  private Text txtSearch;
  private Text txtEntry;
  private TableViewer tvText;

  public TextEditorWindow(Shell parent) {
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
    
    Label lblSearch = new Label(cmpLeft, SWT.NONE);
    lblSearch.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
    lblSearch.setText("Search:");
    
    txtSearch = new Text(cmpLeft, SWT.BORDER);
    txtSearch.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
    
    tvText = new TableViewer(cmpLeft, SWT.BORDER | SWT.FULL_SELECTION);
    Table tblText = tvText.getTable();
    tblText.setHeaderVisible(true);
    tblText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
    
    TableViewerColumn colViewerText = new TableViewerColumn(tvText, SWT.NONE);
    TableColumn colText = colViewerText.getColumn();
    colText.setWidth(314);
    colText.setText("Text");
    
    TableViewerColumn colViewerId = new TableViewerColumn(tvText, SWT.NONE);
    TableColumn colId = colViewerId.getColumn();
    colId.setWidth(226);
    colId.setText("ID");
    new Label(cmpLeft, SWT.NONE);
    new Label(cmpLeft, SWT.NONE);
    
    Composite cmpRight = new Composite(sashForm, SWT.NONE);
    cmpRight.setLayout(new GridLayout(1, false));
    
    Label lblTextEntry = new Label(cmpRight, SWT.NONE);
    lblTextEntry.setText("Text Entry:");
    
    txtEntry = new Text(cmpRight, SWT.BORDER | SWT.MULTI);
    GridData gd_txtEntry = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
    gd_txtEntry.heightHint = 193;
    txtEntry.setLayoutData(gd_txtEntry);
    sashForm.setWeights(new int[] {590, 423});
    
    CoolBar coolBar = new CoolBar(shell, SWT.FLAT);
    
    CoolItem coolItem = new CoolItem(coolBar, SWT.NONE);
    
    Label lblLanguage = new Label(coolBar, SWT.NONE);
    coolItem.setControl(lblLanguage);
    lblLanguage.setText("Language");

    
    
    
    fillContents();
    
    
  }

  private void fillContents() {
    tvText.setContentProvider(ArrayContentProvider.getInstance());
    tvText.setLabelProvider(new Labels());
    tvText.setInput(JmmoContext.getBean(IntlStringService.class).loadAll());
    
  }

}
