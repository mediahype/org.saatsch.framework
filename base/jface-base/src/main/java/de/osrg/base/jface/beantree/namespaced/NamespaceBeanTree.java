package de.osrg.base.jface.beantree.namespaced;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

/**
 * 
 * NamespaceBeanTree wraps a TreeViewer. That TreeViewer takes as input a  <code> Map&lt;String,Object&gt;</code>.
 * 
 * @author saatsch
 *
 */
public class NamespaceBeanTree extends Composite {

  private TreeViewer treeViewer;

  public NamespaceBeanTree(Composite parent) {
    super(parent, SWT.NONE);
    setLayout(new FillLayout(SWT.HORIZONTAL));


    treeViewer = new TreeViewer(this, SWT.BORDER);

    treeViewer.setLabelProvider(new NameProvider());
    treeViewer.setContentProvider(new NamespaceContentProvider());
  }



  /**
   * clients may do what they want with the treeviewer (for example install handlers)
   * 
   * @return the internal {@link TreeViewer}
   */
  public TreeViewer get() {
    return treeViewer;
  }

}
