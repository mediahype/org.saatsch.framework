package org.saatsch.framework.jmmo.data.editor.ui.tabcontent.inspect;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.joda.beans.Bean;

public class InspectComposite extends Composite {

  private TreeViewer treeViewer;


  public InspectComposite(Composite parent) {
    super(parent, SWT.NONE);
    setLayout(new FillLayout(SWT.HORIZONTAL));
    
    treeViewer = new TreeViewer(this, SWT.BORDER);
    Tree tree = treeViewer.getTree();

  }

  
  public void inspect(Bean bean, Class baseClass){
    
    InspectContentProvider provider = new InspectContentProvider(bean);
    treeViewer.setContentProvider(provider);
    treeViewer.setLabelProvider(new InspectLabelProvider());
    treeViewer.setInput(bean);
    
  }
  

}
