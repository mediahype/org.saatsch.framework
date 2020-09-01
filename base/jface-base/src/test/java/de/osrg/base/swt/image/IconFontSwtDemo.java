package de.osrg.base.swt.image;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import jiconfont.icons.font_awesome.FontAwesome;

public class IconFontSwtDemo {

  private final class CellLabelProviderExtension extends CellLabelProvider {
    @Override
    public void update(ViewerCell cell) {
      // TODO Auto-generated method stub
      
    }
    
    
    
  }


  protected Shell shell;
  private Table table;
  private TableColumn tblclmnConst;
  private TableViewerColumn tableViewerColumn_1;


  /**
   * Launch the application.
   * 
   * @param args
   */
  public static void main(String[] args) {

    try {
      IconFontSwtDemo demo = new IconFontSwtDemo();

      demo.open();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Open the window.
   */
  public void open() {
    Display display = Display.getDefault();
    createContents();
    shell.open();
    shell.layout();
    while (!shell.isDisposed()) {
      if (!display.readAndDispatch()) {
        display.sleep();
      }
    }
  }

  /**
   * Create contents of the window.
   */
  protected void createContents() {
    shell = new Shell();
    shell.setSize(865, 279);
    shell.setText(getClass().getName());
    shell.setLayout(new GridLayout(1, false));

    Composite composite = new Composite(shell, SWT.NONE);
    composite.setLayout(new RowLayout(SWT.HORIZONTAL));

    new Button(composite, SWT.NONE).setImage(new Image(Display.getDefault(), IconFontSwt.buildSwtIcon(FontAwesome.BTC, 16, new Color(0, 0, 0))));
    new Button(composite, SWT.NONE).setImage(new Image(Display.getDefault(), IconFontSwt.buildSwtIcon(FontAwesome.BTC, 32, new Color(0, 0, 0))));
    new Button(composite, SWT.NONE).setImage(new Image(Display.getDefault(), IconFontSwt.buildSwtIcon(FontAwesome.BTC, 64, new Color(255, 255, 255))));
    

    
    TableViewer tableViewer = new TableViewer(shell, SWT.BORDER | SWT.FULL_SELECTION);
    table = tableViewer.getTable();
    table.setHeaderVisible(true);
    table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
    
    TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
    tableViewerColumn.setLabelProvider(new CellLabelProviderExtension());
    TableColumn tblclmnImage = tableViewerColumn.getColumn();
    tblclmnImage.setWidth(100);
    tblclmnImage.setText("Image");
    
    tableViewerColumn_1 = new TableViewerColumn(tableViewer, SWT.NONE);
    tableViewerColumn_1.setLabelProvider(new CellLabelProviderExtension());
    tblclmnConst = tableViewerColumn_1.getColumn();
    tblclmnConst.setWidth(100);
    tblclmnConst.setText("Const");
    
   
    
  }

  

  


}
