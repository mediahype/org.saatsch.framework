package de.osrg.base.swt;

/*
 * #%L
 * nof-swt
 * $Id:$
 * $HeadURL:$
 * %%
 * Copyright (C) 2013 http://nof.sf.net
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

public class MessageBoxUtil {

	/**
	 * shows an info message box.
	 * 
	 * @param message
	 *            the message
	 * @param parent
	 *            the parent {@link Shell} of the mesasage box.
	 */
	public static void showInfoMessage(String message, Shell parent) {
		MessageBox mb = new MessageBox(parent, SWT.ICON_INFORMATION);
		mb.setText("Info");
		mb.setMessage(message);
		mb.open();
	}

	/**
	 * shows an error message box.
	 * 
	 * @param message
	 *            the message
	 * @param parent
	 *            the parent {@link Shell} of the mesasage box.
	 */
	public static void showErrorMessage(String message, Shell parent) {
		MessageBox mb = new MessageBox(parent, SWT.ICON_ERROR);
		mb.setText("Error");
		mb.setMessage(message == null ? "null" : message);
		mb.open();
	}

	/**
	 * shows a ui which lets the user input a string.
	 * 
	 * @param question
	 *            the question
	 * @param parent
	 *            the parent {@link Shell}
	 * @return the answer from the user.
	 */
	public static String promptForString(String question, Shell parent) {
		InputDialog dia = new InputDialog(parent, question);
		return dia.open();
	}

	public static void showWarningMessage(String warn, Shell shell) {
		MessageBox mb = new MessageBox(shell, SWT.ICON_ERROR);
		mb.setText("Warning");
		mb.setMessage(warn);
		mb.open();

	}

	/**
	 * @param question
	 *            the question to ask
	 * @param shell
	 *            the {@link Shell}
	 * @return <code>true</code> if the user answered yes
	 */
	public static boolean askYesNo(String question, Shell shell) {
		MessageBox mb = new MessageBox(shell, SWT.ICON_QUESTION | SWT.YES | SWT.NO);
		mb.setMessage(question);
		int ret = mb.open();

		return ret == SWT.YES;

	}

}
