package org.saatsch.framework.jmmo.data.editor.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GridView extends Composite {

  private static final Logger LOG = LoggerFactory.getLogger(GridView.class);

  public GridView(Composite parent) {
    super(parent, SWT.NONE);


    GridLayout layout = new GridLayout(1, false);
    layout.verticalSpacing = 0;
    layout.horizontalSpacing = 0;
    setLayout(layout);


  }
}
