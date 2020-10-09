package org.saatsch.framework.jmmo.data.editor.ui.composite.listproperties;

import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

public abstract class AbstractPropertyLabelProvider extends LabelProvider implements IColorProvider {


  @Override
  public Color getForeground(Object element) {
    return Display.getCurrent().getSystemColor(SWT.COLOR_BLACK);
  }

  @Override
  public Color getBackground(Object element) {
    return Display.getCurrent().getSystemColor(SWT.COLOR_WHITE);
  }

 
  
}
