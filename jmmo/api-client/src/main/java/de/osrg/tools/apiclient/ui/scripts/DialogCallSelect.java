package de.osrg.tools.apiclient.ui.scripts;


import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.wb.swt.SWTResourceManager;

import de.osrg.base.swt.DialogUtil;
import de.osrg.tools.apiclient.ApiClientUserData;
import de.osrg.tools.apiclient.model.MethodCallVO;


/**
 * 
 */
public class DialogCallSelect extends Dialog {

  private Shell shell;

  private Tree treeRequests;

  private MethodCallVO selectedCall;

  public DialogCallSelect(Shell parent) {
    super(parent);

  }

  /**
   * Open the dialog.
   * 
   * @return the result
   */
  public MethodCallVO open() {
    createContents();
    shell.open();
    shell.layout();
    Display display = getParent().getDisplay();
    while (!shell.isDisposed()) {
      if (!display.readAndDispatch()) {
        display.sleep();
      }
    }
    return selectedCall;

  }

  private void createContents() {
    shell = new Shell(getParent(), SWT.BORDER | SWT.CLOSE | SWT.PRIMARY_MODAL);
    shell.setSize(367, 477);
    shell.setText("Select Call");

    treeRequests = new Tree(shell, SWT.BORDER);
    treeRequests.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseDoubleClick(MouseEvent e) {

        if (e.getSource() instanceof Tree) {

          Tree tree = (Tree) e.getSource();
          TreeItem ti = tree.getSelection()[0];
          selectedCall = (MethodCallVO) ti.getData();
          shell.dispose();
        }

      }
    });
    treeRequests.setBounds(10, 10, 339, 376);

    Label lblInfo_1 = new Label(shell, SWT.WRAP);
    lblInfo_1.setAlignment(SWT.CENTER);
    lblInfo_1.setFont(SWTResourceManager.getFont("Tahoma", 10, SWT.NORMAL));
    lblInfo_1.setBounds(10, 403, 339, 39);
    lblInfo_1.setText(
        "The selected Call is being COPIED. This means that changes to the original call will not affect calls in the script.");

    fillContents();
    
    DialogUtil.center(shell, getParent());
    
  }

  private void fillContents() {


    for (MethodCallVO call : ApiClientUserData.getUserData().getMethodCalls()) {
      TreeItem ti = new TreeItem(treeRequests, SWT.NONE);
      ti.setText(call.getName());
      ti.setData(call);
    }
  }
}
