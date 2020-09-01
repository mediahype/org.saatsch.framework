package de.osrg.tools.apiclient.ui;

import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import de.osrg.base.swt.support.AbstractTreeRenamer;
import de.osrg.tools.apiclient.model.MethodCallVO;

public class CmdRenameCall extends AbstractTreeRenamer {

  private ApiClientMainUI client;

  public CmdRenameCall(ApiClientMainUI apiClientMainUI) {
    this.client = apiClientMainUI;
  }
  
  @Override
  public Tree getTree() {
    return client.getTreeCalls();
  }

  @Override
  public void renamed(TreeItem item, Text text) {
    ((MethodCallVO)item.getData()).setName(text.getText());
    
  }

}
