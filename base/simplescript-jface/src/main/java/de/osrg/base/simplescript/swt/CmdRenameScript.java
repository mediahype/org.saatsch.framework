package de.osrg.base.simplescript.swt;

import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import de.osrg.base.simplescript.Simplescript;
import de.osrg.base.swt.support.AbstractTreeRenamer;

public class CmdRenameScript extends AbstractTreeRenamer{

  private ScriptsUI client;

  public CmdRenameScript(ScriptsUI scriptsUI) {
    this.client = scriptsUI;
  }

  @Override
  public Tree getTree() {
    return client.getTreeScripts();
  }

  @Override
  public void renamed(TreeItem item, Text text) {
    ((Simplescript) item.getData()).setName(text.getText());
  }



}
