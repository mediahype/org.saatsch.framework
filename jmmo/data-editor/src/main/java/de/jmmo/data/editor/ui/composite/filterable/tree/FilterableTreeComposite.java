package de.jmmo.data.editor.ui.composite.filterable.tree;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.joda.beans.Bean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.jmmo.data.api.PropertyUtil;
import de.jmmo.data.editor.ui.composite.filterable.JmmoBeanFilter;
import de.jmmo.data.editor.ui.composite.filterable.JmmoBeanLabelProvider;
import de.jmmo.data.editor.ui.dialog.EditObjectDialog;
import de.jmmo.data.editor.ui.tabcontent.EditorTabContent;

public abstract class FilterableTreeComposite extends Composite {

  private static final Logger LOG = LoggerFactory.getLogger(FilterableTreeComposite.class);

  private Text txtFilter;
  private JmmoBeanFilter listFilter;

  private TreeViewer treeViewer;
  private Tree tree;

  public FilterableTreeComposite(Composite parent) {
    super(parent, SWT.NONE);

    setLayout(new GridLayout(1, false));

    txtFilter = new Text(this, SWT.BORDER);
    txtFilter.addModifyListener(event -> {
      // TODO: only search and refresh after user input pause.

      Object selected = null;
      if (getCurrentlySelected() != null) {
        selected = getCurrentlySelected();
      }

      listFilter.setSearchText(txtFilter.getText());
      treeViewer.refresh();

      if (null != selected) {
        makeSelected(selected);
      }

      // TODO: if filter not empty, show first result

    });

    txtFilter.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

    listFilter = new JmmoBeanFilter();



    treeViewer = new TreeViewer(this, SWT.BORDER);
    tree = treeViewer.getTree();
    tree.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseDoubleClick(MouseEvent e) {

        Composite tabcontent = findParentTabcontent(parent);
        if (null != tabcontent) {
          EditObjectDialog dia = new EditObjectDialog((EditorTabContent) tabcontent);
          dia.open();
        }

      }
    });
    tree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
    treeViewer.setContentProvider(new FilterableTreeContentProvider(this));
    treeViewer.setLabelProvider(new JmmoBeanLabelProvider());

    treeViewer.addFilter(listFilter);



  }

  private Composite findParentTabcontent(Composite c) {
    if (c == null) {
      return null;
    }
    
    if (c instanceof EditorTabContent) {
      return c;
    }
    
    return findParentTabcontent(c.getParent());
    
  }

  protected void init() {
    ISelectionChangedListener listener = getSelectionChangedListener();
    if (null != listener) {
      treeViewer.addSelectionChangedListener(listener);
    }

  }

  public void setContextMenu(Menu menu) {
    treeViewer.getTree().setMenu(menu);
  }

  /**
   * refreshes the list view of this {@link FilterableTreeComposite} (e.g. after an object has been
   * added or deleted)
   */
  public void refresh() {
    treeViewer.setInput("irrelevant");
  }

  protected abstract java.util.List getContentData();

  /**
   * if the implementation needs to be informed when the selection changes it must return a listener
   * here, if not, it may return <code>null</code>.
   * 
   * @return an {@link ISelectionChangedListener} or <code>null</code>
   */
  protected abstract ISelectionChangedListener getSelectionChangedListener();



  public Object getCurrentlySelected() {
    ITreeSelection selection = treeViewer.getStructuredSelection();
    return selection.getFirstElement();
  }

  // TODO: does not work. Should select the given Object.
  public void makeSelected(Object o) {

    if (!isBean(o)) {
      LOG.info("given object is not a bean... cannot select.");
      return;
    }

    String appId = appId(o);
    TreeItem[] items = treeViewer.getTree().getItems();


    findItem(items, appId);

  }

  private void findItem(TreeItem[] items, String appId) {

    for (int i = 0; i < items.length; i++) {
      if (isBean(items[i].getData()) && appId(items[i].getData()).equals(appId)) {
        LOG.info("found item ... selecting ...");
        treeViewer.getTree().select(items[i]);
        treeViewer.getTree().showSelection();
        return;
      }
      if (items[i].getItems().length > 0) {
        findItem(items[i].getItems(), appId);
      }
    }

  }


  /**
   * returns <code>true</code> if the given object is a Bean
   * 
   * @param o the object
   * @return <code>true</code> if the given object is a Bean
   */
  private boolean isBean(Object o) {
    return o instanceof Bean;
  }

  private String appId(Object o) {
    return PropertyUtil.getAppId((Bean) o);
  }

  public void refreshTreeViewer() {
    Object[] elements = treeViewer.getExpandedElements();
    TreePath[] treePaths = treeViewer.getExpandedTreePaths();
    treeViewer.refresh();
    treeViewer.setExpandedElements(elements);
    treeViewer.setExpandedTreePaths(treePaths);
  }

}
