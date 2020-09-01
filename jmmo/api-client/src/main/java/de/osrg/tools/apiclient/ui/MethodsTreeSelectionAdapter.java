package de.osrg.tools.apiclient.ui;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import de.osrg.tools.apiclient.model.MethodCallVO;

public class MethodsTreeSelectionAdapter extends SelectionAdapter {
  
  private MethodWrapperUI ui;
  
  public MethodsTreeSelectionAdapter(MethodWrapperUI ui) {
    this.ui = ui;
  }


  @Override
  public void widgetSelected(SelectionEvent e) {

    Tree treeTasks = (Tree) e.getSource();

    TreeItem selected = treeTasks.getSelection()[0];

    if (selected.getData() instanceof AbstractMethod) {
      ui.setMethod((AbstractMethod) selected.getData());
    }
    if (selected.getData() instanceof MethodCallVO) {
      ui.setMethod((MethodCallVO) selected.getData());
    }

  }
}
