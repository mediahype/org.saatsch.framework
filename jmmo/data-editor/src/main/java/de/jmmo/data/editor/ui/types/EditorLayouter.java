package de.jmmo.data.editor.ui.types;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;

public class EditorLayouter {

  private Boolean currentLayoutRow = false;
  private Composite currentComposite;
  private Composite client;

  // the style with which new composites are created
  private int style = SWT.NONE;


  public EditorLayouter(Composite client) {
    this.client = client;
  }


  public void nextLayoutIsRow() {

    // the current composite is already row. nothing to do.
    if (currentLayoutRow) {
      return;
    }



    currentComposite = new DebugComposite(client, style);
    RowLayout layout = new RowLayout(SWT.HORIZONTAL);
    layout.marginHeight = 0;
    layout.marginWidth = 0;
    layout.marginLeft = 0;
    layout.marginRight = 0;

    currentComposite.setLayout(layout);

    if (client.getLayout() instanceof GridLayout) {
      currentComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
    }

    currentLayoutRow = true;
  }

  /**
   * grid layout is used for tables. Every call to this method creates a new child composite.
   */
  public void nextLayoutIsGrid() {


    currentComposite = new DebugComposite(client, style);
    GridLayout layout = new GridLayout();
    layout.marginBottom = 0;
    layout.marginTop = 0;
    layout.marginLeft = 0;
    layout.marginRight = 0;
    layout.marginHeight = 0;
    layout.marginWidth = 0;
    layout.horizontalSpacing = 0;
    layout.verticalSpacing = 0;


    currentComposite.setLayout(layout);

    if (client.getLayout() instanceof GridLayout) {
      currentComposite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
    } else {
      RowData rowData = new RowData();
      currentComposite.setLayoutData(rowData);

    }

    currentLayoutRow = false;
  }

  public Composite getToDrawInto() {
    return currentComposite;
  }

}
