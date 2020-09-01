package de.osrg.base.swt;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Shell;

public abstract class OpenableDialog extends Dialog {

  public OpenableDialog(Shell parent, int style) {
    super(parent, style);
  }

  public OpenableDialog(Shell parent) {
    super(parent);
  }

  public abstract Object open();
  
}
