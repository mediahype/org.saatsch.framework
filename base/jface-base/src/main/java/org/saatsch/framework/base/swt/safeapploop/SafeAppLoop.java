package org.saatsch.framework.base.swt.safeapploop;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.saatsch.framework.base.swt.MessageBoxUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.saatsch.framework.base.UserException;

/**
 * The SafeAppLoop is the last line of defense against checked Exceptions coming out of an SWT {@link Display} loop.<br>
 * 
 * Runs an SWT App Loop that does not end on checked exceptions and does not end on
 * {@link UserException}s. It <em>does</em> end (let the program crash) on any RuntimeException that is not a {@link UserException} or
 * subclass thereof. Before the safe app loop runs, the content of the shell must be created.
 * 
 * @author saatsch
 *
 */
public class SafeAppLoop {

  private static final String ERROR = "Error: ";
  private static final Logger LOG = LoggerFactory.getLogger(SafeAppLoop.class);

  private SafeAppLoop() {}

  /**
   * this is the only method in this class so for docs see the javadoc {@link SafeAppLoop of the class}.
   * This method blocks until the user closes the shell.
   * 
   * @see SafeAppLoop 
   * @param shell the shell
   * @param display the display
   */
  public static void run(Shell shell, Display display) {

    while (!shell.isDisposed()) {
      boolean readAndDispatch = false;
      try {
        readAndDispatch = display.readAndDispatch();
      } catch (UserException e) {
        MessageBoxUtil.showErrorMessage("Input Error: " + e.getMessage(), shell);
        LOG.error(e.getMessage());
      } catch (Exception e) {
        MessageBoxUtil.showErrorMessage(ERROR + e.getMessage(), shell);
        LOG.error(ERROR, e);
      }
      if (!readAndDispatch) {
        display.sleep();
      }
    }

  }

}
