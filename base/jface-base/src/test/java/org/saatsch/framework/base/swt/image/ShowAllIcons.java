package org.saatsch.framework.base.swt.image;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.StyledCellLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import jiconfont.icons.font_awesome.FontAwesome;

/**
 * renders and shows all available icons from the {@link FontAwesome}.
 * 
 * @author saatsch
 *
 */
public class ShowAllIcons {



  public static void main(String[] args) {
    final Display display = new Display();
    final Shell shell = new Shell(display);
    shell.setLayout(new GridLayout(1, true));

    TableViewer viewer = createViewer(shell);

    System.out.println( FontAwesome.values().length + " icons" );

    viewer.setInput(createModel());


    shell.pack();
    shell.open();
    while (!shell.isDisposed()) {
      if (!display.readAndDispatch()) {
        display.sleep();
      }
    }
    display.dispose();
  }

  private static TableViewer createViewer(final Shell shell) {
    TableViewer viewer =
        new TableViewer(shell, SWT.FULL_SELECTION | SWT.H_SCROLL | SWT.V_SCROLL | SWT.NONE);

    viewer.setContentProvider(ArrayContentProvider.getInstance());

    viewer.getTable().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

    TableViewerColumn col = new TableViewerColumn(viewer, SWT.NONE);
    col.getColumn().setWidth(100);
    col.getColumn().setText("Text Column");
    col.setLabelProvider(new StyledCellLabelProvider() {
      @Override
      public void update(ViewerCell cell) {
        ConstPlusImage element = (ConstPlusImage) cell.getElement();
        cell.setText(element.getKey().toString());
      }
    });

    col = new TableViewerColumn(viewer, SWT.NONE);
    col.getColumn().setWidth(100);
    col.getColumn().setText("Image Column");
    col.setLabelProvider(new StyledCellLabelProvider() {
      @Override
      public void update(ViewerCell cell) {
        ConstPlusImage element = (ConstPlusImage) cell.getElement();
        cell.setImage(element.getImage());
      }
    });

    viewer.getTable().setHeaderVisible(true);

    return viewer;
  }

  private static List<ConstPlusImage> createModel() {
    
    List<ConstPlusImage> list = new ArrayList<ConstPlusImage>();
    
    for (FontAwesome awe : FontAwesome.values()) {
      list.add(new ConstPlusImage(awe));
    }
    
    return list;
    
  }

}
