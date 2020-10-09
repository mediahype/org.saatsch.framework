package org.saatsch.framework.jmmo.tools.apiclient.ui;

import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import org.saatsch.framework.base.swt.support.AbstractTreeRenamer;
import org.saatsch.framework.jmmo.tools.apiclient.model.MethodCallVO;

public class CmdRenameCall extends AbstractTreeRenamer {

  private ApiClientMainC client;

  public CmdRenameCall(ApiClientMainC apiClientMainUI) {
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
