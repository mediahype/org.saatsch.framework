package de.osrg.base.swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.events.TraverseEvent;

/**
 * prompt the user to input a String
 * 
 * @author saatsch
 * 
 */
public class InputDialog extends Dialog {

	protected Shell shlInputString;
	private Text txtInput;
	private final String prompt;
	private String result;

	/**
	 * Create the dialog.
	 * 
	 * @param parent
	 * @param question
	 */
	public InputDialog(Shell parent, String question) {
		super(parent);
		this.prompt = question;
	}

	/**
	 * Open the dialog.
	 * 
	 * @return the String the user entered.
	 */
	public String open() {
		createContents();
		shlInputString.open();
		shlInputString.layout();
		txtInput.setFocus();
		Display display = getParent().getDisplay();
		while (!shlInputString.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}

		return result;

	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shlInputString = new Shell(getParent(), SWT.DIALOG_TRIM
				| SWT.APPLICATION_MODAL);
		shlInputString.setSize(315, 124);
		shlInputString.setText("Input");

		Button btnOk = new Button(shlInputString, SWT.NONE);
		btnOk.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				exit();
			}
		});
		btnOk.setBounds(231, 66, 68, 23);
		btnOk.setText("OK");

		Label lblPrompt = new Label(shlInputString, SWT.NONE);
		lblPrompt.setBounds(10, 10, 289, 25);
		lblPrompt.setText(prompt);

		txtInput = new Text(shlInputString, SWT.BORDER);
		txtInput.addTraverseListener(new TraverseListener() {
			public void keyTraversed(TraverseEvent e) {
				if (e.detail == SWT.TRAVERSE_RETURN) {
					exit();
				}
			}
		});

		txtInput.setBounds(10, 41, 289, 19);

	}

	private void exit() {
		result = txtInput.getText();
		shlInputString.dispose();
	}
}
