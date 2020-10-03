package de.jmmo.data.editor.ui.composite.listproperties;

import java.util.Collection;

import org.eclipse.jface.viewers.ColumnViewerToolTipSupport;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.joda.beans.Bean;
import org.joda.beans.Property;

import de.jmmo.cdi.container.JmmoContext;

/**
 * abstract base class that contains a {@link TreeViewer} that displays the properties of a
 * {@link Bean}.
 * 
 * @author saatsch
 *
 */
public abstract class AbstractPropertyTreeC extends Composite {

  protected Bean object;
  protected TreeViewer treeViewer;

  public AbstractPropertyTreeC(Composite parent, Bean object) {
    super(parent, SWT.NONE);
    this.object = object;
    setLayout(new GridLayout(1, false));

    treeViewer = new TreeViewer(this, SWT.BORDER);
    Tree tree = treeViewer.getTree();
    tree.setHeaderVisible(true);
    tree.setLinesVisible(true);
    tree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
    
    
    treeViewer.setContentProvider(new PropertiesProvider());

    ColumnViewerToolTipSupport.enableFor(treeViewer);

  }

  public TreeViewer getTreeViewer() {
    return treeViewer;
  }


  protected Class<Object> typeOfBuffered() {
    return JmmoContext.getBean(CopyPasteBuffer.class).getBuffer().metaProperty().propertyType();
  }

  protected Property<Object> buffered() {
    return JmmoContext.getBean(CopyPasteBuffer.class).getBuffer();
  }

  protected Class<Object> typeOfSelected() {
    return selected().metaProperty().propertyType();
  }

  protected void doCopy() {
    JmmoContext.getBean(CopyPasteBuffer.class).setBuffer(selected());
  }

  protected Property<Object> selected() {
    return (Property<Object>) treeViewer.getStructuredSelection().getFirstElement();
  }


  /**
   * the copy-buffer can be pasted if there is something in the copy-buffer, if neither selected nor
   * buffered element is a collection (NYI) and the types of the element in the copy-buffer and the
   * type of the currently selected element are the same.
   * 
   * @return <code>true</code> if the copy-buffer can be pasted (if pressing CTRL+V must have an
   *         effect), otherwise <code>false</code>
   */
  protected boolean canBePasted() {

    if (null == buffered()) {
      return false;
    }

    // Collections are NYI
    if (selected().get() instanceof Collection || buffered().get() instanceof Collection) {
      return true;
    }
    
    // types are equal -> can be pasted
    if (typeOfBuffered().equals(typeOfSelected())) {
      return true;
    }
    return false;
  }

}
