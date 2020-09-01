package de.osrg.base.swt.widgets.logintercept;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.wb.swt.SWTResourceManager;

public class LogComposite extends Composite {
  
  private static final String WARN = "WARN";

  private static final String ERROR = "ERROR";
  
  private static final Color ORANGE = new Color(Display.getDefault(), 255, 128, 0);
  
  private StyledText text;

  public LogComposite(Composite parent) {
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
  public void append(String s) {

    Display.getDefault().asyncExec(() -> {
      
      int oldCount = 0;
      
      if (shouldColorize(s)) {
        oldCount = text.getCharCount();
      }
      
      text.append(s + "\n");
      text.setTopIndex(text.getLineCount() - 5);

      if (shouldColorize(s))
      {
        StyleRange style1 = new StyleRange();
        style1.start = oldCount;
        style1.length = s.length();
        style1.foreground = systemColor(s);
        text.setStyleRange(style1);
        
      }
      
    });

  }

  private boolean shouldColorize(String s) {
    return s.contains(ERROR) || s.contains(WARN);
  }
  
  private Color systemColor(String s) {
    if (s.contains(ERROR)) {
      return Display.getDefault().getSystemColor(SWT.COLOR_RED);
    } else if (s.contains(WARN)) {
      return ORANGE;
    }
    
    return Display.getDefault().getSystemColor(SWT.COLOR_BLACK);
    
    
  }
  
  /**
   * clears the text area.
   */
  public void clear() {
    Display.getDefault().asyncExec(() -> {
      text.setText("");

    });
    
  }
  
}
