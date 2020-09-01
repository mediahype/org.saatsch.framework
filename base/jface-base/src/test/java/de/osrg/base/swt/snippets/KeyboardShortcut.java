package de.osrg.base.swt.snippets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;

/**
 * Demonstrates how to install a keyboard shortcut that really works app wide.
 * 
 * @author 
 *
 */
public class KeyboardShortcut {

  private void installKeyboardShortcuts() {
    Display display = Display.getDefault();
    display.addFilter(SWT.KeyDown, e -> {
      if (((e.stateMask & SWT.CTRL) == SWT.CTRL) && (e.keyCode == 'e')) {
        // CTRL-e pressed
      } else if (((e.stateMask & SWT.CTRL) == SWT.CTRL) && (e.keyCode == 'i')) {
        // CTRL-i pressed
      }
    });
    
  }
  
}
