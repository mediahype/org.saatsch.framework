package de.osrg.base.swt.widgets.logintercept;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.wb.swt.SWTResourceManager;

public abstract class AbstractLogComposite extends Composite {

  private StyledText text;

  public AbstractLogComposite(Composite parent) {
    super(parent, SWT.NONE);
    setLayout(new FillLayout(SWT.HORIZONTAL));

    text = new StyledText(this, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
    text.setFont(SWTResourceManager.getFont("Courier New", 8, SWT.NORMAL));


  }

  /**
   * appends a string (and then a newline) to the log. This may be called from any Tread.
   * 
   * @param s the String to append.
   */
  public void append(Object s) {

    Display.getDefault().asyncExec(() -> {
      
      int currentCharCount = text.getCharCount();

      
      text.append(getText(s) + "\n");
      text.setTopIndex(text.getLineCount() - 5);

      if (shouldColorize(s))
      {
        StyleRange style1 = new StyleRange();
        style1.start = currentCharCount;
        style1.length = s.toString().length();
        style1.foreground = getForeground(s);
        text.setStyleRange(style1);
        
      }
      
    });

  }

  protected abstract boolean shouldColorize(Object s);
  
  protected abstract Color getForeground(Object s);
  
  protected abstract String getText(Object s);
  
  
  /**
   * clears the text area.
   */
  public void clear() {
    Display.getDefault().asyncExec(() -> {
      text.setText("");

    });
    
  }
  
}
