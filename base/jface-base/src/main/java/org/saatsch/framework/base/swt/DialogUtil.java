package org.saatsch.framework.base.swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

public class DialogUtil {

  private DialogUtil() {}

  /**
   * centers the given shell relative to the given parent
   * 
   * @param shell
   * @param parent
   */
  public static void center(Shell shell, Shell parent) {


    Rectangle bounds = parent.getBounds();
    Rectangle rect = shell.getBounds();

    int x = bounds.x + (bounds.width - rect.width) / 2;
    int y = bounds.y + (bounds.height - rect.height) / 2;

    shell.setLocation(x, y);

  }

  /**
   * installs a given runnable to be executed when the enter key is pressed on the given control.
   * 
   * @param runnable the {@link Runnable} to install.
   * @param control the {@link Control} to install the enter key listener onto.
   */
  public static void execOnEnter(Runnable runnable, Control control) {
    control.addTraverseListener(new TraverseListener() {
      @Override
      public void keyTraversed(TraverseEvent e) {
        if (e.detail == SWT.TRAVERSE_RETURN) {
          runnable.run();          
        }
      }
    });
  }


}
