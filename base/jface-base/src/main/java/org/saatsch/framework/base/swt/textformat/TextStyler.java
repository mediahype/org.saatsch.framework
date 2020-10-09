package org.saatsch.framework.base.swt.textformat;

import java.util.Stack;

import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;

import org.saatsch.framework.base.util.Color;
import org.saatsch.framework.base.util.KeyValuePair;
import org.saatsch.framework.base.util.textformat.Applyable;
import org.saatsch.framework.base.util.textformat.ApplyableColor;
import org.saatsch.framework.base.util.textformat.Format;
import org.saatsch.framework.base.util.textformat.FormattableText;

/**
 * Bridge between SWT and {@link FormattableText}.
 * 
 * @author saatsch
 *
 */
public class TextStyler {

  private Stack<Format> colorStack = new Stack<Format>();

  public void style(StyledText txtFormatted, FormattableText text) {

    txtFormatted.setText(text.getUnformatted());

    for (Format format : text.getFormats()) {
      if (format.getTag().getName().equals(Applyable.COLOR_TAG_NAME)) {
        applyColor(txtFormatted, format);
      }
    }

  }

  private void applyColor(StyledText txtFormatted, Format format) {
    if (format.getTag().isStart()) {
      openColor(format);
    } else {
      closeColor(txtFormatted, format);
    }

  }

  private void closeColor(StyledText txtFormatted, Format closing) {
    Format opening = colorStack.pop();
    StyleRange range = new StyleRange();

    range.start = opening.getOffset();
    range.length = closing.getOffset() - opening.getOffset();

    KeyValuePair keyValuePair = opening.getTag().getKeyValuePairByKey(ApplyableColor.COLOR_VALUE);
    if (null != keyValuePair) {
      range.foreground = ColorFactory.getInstance()
          .getColor(Color.fromHexString(keyValuePair.getValue().toString()));
    }


    txtFormatted.setStyleRange(range);

  }

  private void openColor(Format format) {
    colorStack.push(format);
  }

}
