package org.saatsch.framework.base.swt.textformat;

import org.eclipse.swt.custom.StyledTextContent;
import org.saatsch.framework.base.swt.AbstractDemo;
import org.saatsch.framework.base.util.Color;
import org.saatsch.framework.base.util.textformat.ApplyableColor;
import org.saatsch.framework.base.util.textformat.FormattableText;
import org.saatsch.framework.base.util.textformat.Newline;

public class TextformatDemo extends AbstractDemo {

  Newline newLine = new Newline();
  ApplyableColor red = new ApplyableColor(new Color(230, 26, 122));
  ApplyableColor blue = new ApplyableColor(new Color(0, 0, 255));

  
  public static void main(String[] args) {
    
    TextformatDemo b = new TextformatDemo();
    b.open();
    
  }
  
  @Override
  protected void createContents() {
   
    TextFormatC composite = new TextFormatC(content);
    
    FormattableText formattable = composite.getFormattedText();

    formattable.append("redBlue");

    formattable.apply(red, 0, 3);

    formattable.apply(blue, 3, 7);

    composite.updateYourself();
    
    StyledTextContent styledTextContent = composite.getStyledTextWidget().getContent();
    System.out.println(styledTextContent);
    
  }

}
