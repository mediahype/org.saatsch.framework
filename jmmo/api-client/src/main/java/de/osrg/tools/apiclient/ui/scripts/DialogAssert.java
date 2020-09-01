package de.osrg.tools.apiclient.ui.scripts;

import javax.el.ELException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.jmmo.cdi.container.JmmoContext;
import de.osrg.base.expressions.ExpressionsException;
import de.osrg.base.simplescript.OpenableEditor;
import de.osrg.base.swt.DialogUtil;
import de.osrg.base.swt.MessageBoxUtil;
import de.osrg.tools.apiclient.cdi.BeanNamespaceImpl;
import de.osrg.tools.apiclient.model.AssertVO;

/**
 * dialog that edits an AssertVO
 * 
 * @author saatsch
 *
 */
public class DialogAssert extends Dialog implements OpenableEditor<AssertVO> {

  private static final Logger LOG = LoggerFactory.getLogger(DialogAssert.class);
  
  private Shell shell;
  private AssertVO toEdit;
  private Text txtExpression;
  private Text txtExpected;

  public DialogAssert(Shell parent) {
    super(parent);

  }

  /**
   * Open the dialog.
   * 
   * @return the result
   */
  public AssertVO open() {
    createContents();
    shell.open();
    shell.layout();
    Display display = getParent().getDisplay();
    while (!shell.isDisposed()) {
      if (!display.readAndDispatch()) {
        display.sleep();
      }
    }
    return toEdit;

  }

  private void createContents() {
    shell = new Shell(getParent(), SWT.BORDER | SWT.CLOSE | SWT.PRIMARY_MODAL);
    shell.setSize(367, 241);
    shell.setText("Assert");
    shell.setLayout(new GridLayout(1, false));

    Label lblExpression = new Label(shell, SWT.NONE);
    lblExpression.setText("Expression:");

    txtExpression = new Text(shell, SWT.BORDER);
    txtExpression.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1));

    Button btnTestExpression = new Button(shell, SWT.NONE);
    btnTestExpression.setText("Test Expression");
    btnTestExpression.addListener(SWT.Selection, event -> testExpression());

    Label lblEquals = new Label(shell, SWT.NONE);
    lblEquals.setFont(SWTResourceManager.getFont("Tahoma", 14, SWT.NORMAL));
    lblEquals.setText("Equals");

    Label lblExpectedResult = new Label(shell, SWT.NONE);
    lblExpectedResult.setText("Expected Result:");

    txtExpected = new Text(shell, SWT.BORDER);
    txtExpected.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

    Button btnAssert = new Button(shell, SWT.NONE);
    btnAssert.setText("Assert");
    btnAssert.addListener(SWT.Selection, event -> assertIt());

    Button btnOk = new Button(shell, SWT.NONE);
    btnOk.setText("OK");
    btnOk.addListener(SWT.Selection, event -> ok());

    fillContents();

    DialogUtil.center(shell, getParent());

  }

  private void assertIt() {
    try {
      Boolean doAssert = JmmoContext.getBean(BeanNamespaceImpl.class).doAssert(txtExpression.getText(), txtExpected.getText());
      MessageBoxUtil.showInfoMessage("Result: " + doAssert, getParent());
    } catch (ExpressionsException e) {
      MessageBoxUtil.showErrorMessage(e.getMessage(), getParent());
      LOG.error("Error:", e.getMessage());
    }
    
  }

  private void testExpression() {

    try {
      String evaluate =
          JmmoContext.getBean(BeanNamespaceImpl.class).evaluate(txtExpression.getText());
      MessageBoxUtil.showInfoMessage(evaluate, getParent());
    } catch (ELException e) {
      MessageBoxUtil.showErrorMessage(e.getMessage(), getParent());
      LOG.error("Error: {}", e.getMessage());
    }
  }

  private void fillContents() {
    if (toEdit != null) {
      if ( toEdit.getExpected() != null) {
        txtExpected.setText(toEdit.getExpected());
      }
      if (toEdit.getExpression() != null) {
        txtExpression.setText(toEdit.getExpression());
      }
    }

  }

  private void ok() {
    if (toEdit == null) {
      toEdit = new AssertVO();
      toEdit.setName("Assert ...");
    }
    toEdit.setExpression(txtExpression.getText());
    toEdit.setExpected(txtExpected.getText());
    shell.dispose();
  }

  public void setToEdit(AssertVO toEdit) {
    this.toEdit = toEdit;
  }

}
