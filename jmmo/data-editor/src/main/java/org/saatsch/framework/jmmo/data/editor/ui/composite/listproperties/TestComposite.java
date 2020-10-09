package org.saatsch.framework.jmmo.data.editor.ui.composite.listproperties;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;

public class TestComposite extends Composite {

  /**
   * Create the composite.
   * @param parent
   * @param style
   */
  public TestComposite(Composite parent, int style) {
    super(parent, style);
    GridLayout gridLayout = new GridLayout(1, false);
    gridLayout.verticalSpacing = 1;
    gridLayout.marginWidth = 1;
    gridLayout.horizontalSpacing = 1;
    gridLayout.marginHeight = 1;
    setLayout(gridLayout);
    
    SashForm sashForm = new SashForm(this, SWT.NONE);
    sashForm.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
    
    TreeViewer treeViewer = new TreeViewer(sashForm, SWT.BORDER);
    Tree tree = treeViewer.getTree();
    
    TreeViewer treeViewer_1 = new TreeViewer(sashForm, SWT.BORDER);
    Tree tree_1 = treeViewer_1.getTree();
    sashForm.setWeights(new int[] {1, 1});

  }

  @Override
  protected void checkSubclass() {
    // Disable the check that prevents subclassing of SWT components
  }
}
