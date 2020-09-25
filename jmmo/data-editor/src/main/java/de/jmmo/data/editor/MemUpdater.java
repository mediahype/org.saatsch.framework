package de.jmmo.data.editor;

import java.text.NumberFormat;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.CoolItem;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

/**
 * Runnable that updates the given {@link Label} with the memory usage of the current java runtime
 * and then sleeps for 1 second.
 * 
 * @author saatsch
 *
 */
public class MemUpdater implements Runnable {

  private Label lblMem;
  private NumberFormat format = NumberFormat.getInstance();
  private Shell shell;
  private CoolItem coolItem;

  public MemUpdater(Label lblMem, Shell shell, CoolItem coolItem) {
    this.lblMem = lblMem;
    this.shell = shell;
    this.coolItem = coolItem;
  }



  @Override
  public void run() {

    while (!shell.isDisposed()) {
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }

      Display.getDefault().asyncExec(new Runnable() {

        @Override
        public void run() {
          if (!lblMem.isDisposed()) {
            // TODO: this does not update itself any more
            String m = format.format(Runtime.getRuntime().totalMemory() / 1024);
            lblMem.setText("Mem: " + m);
            lblMem.pack();
            Point size = lblMem.getSize();
            coolItem.setSize(coolItem.computeSize(size.x, size.y));
          }
        }
      });


    }

  }

}
