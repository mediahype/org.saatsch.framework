package org.saatsch.framework.base.jface.widgets;

import org.eclipse.jface.window.ToolTip;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;

public class RichTooltip extends ToolTip {

  private String text; 
  
  public RichTooltip(Control control) {
    super(control);
    
  }

  @Override
  protected Composite createToolTipContentArea(Event event, Composite parent) {

    StyledText styled = new StyledText(parent, SWT.NONE);
    if (getText() != null) {
      styled.setText(getText());
      
      StyleRange styleRange = new StyleRange();
      styleRange.start = 0;
      styleRange.length = text.length();
      styleRange.fontStyle = SWT.BOLD;
      styleRange.foreground = Display.getDefault().getSystemColor(SWT.COLOR_BLUE);
      styled.setStyleRange(styleRange);
      
    }
    
    return styled;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

}
