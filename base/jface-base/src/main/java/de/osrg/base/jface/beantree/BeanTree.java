package de.osrg.base.jface.beantree;

import java.util.List;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;


/**
 * BeanTree wraps a TreeViewer. That TreeViewer takes as input a {@link List} of Beans.
 * 
 * @author saatsch
 *
 */
public class BeanTree extends Composite {
  
  private TreeViewer treeViewer;

  public BeanTree(Composite parent) {
    super(parent, SWT.NONE);
    setLayout(new FillLayout(SWT.HORIZONTAL));
    
    
    treeViewer = new TreeViewer(this, SWT.BORDER);
    
    treeViewer.setLabelProvider(new NameProvider());
    treeViewer.setContentProvider(new PropertiesProvider());

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
