package de.osrg.base.swt.textformat;

import java.util.Stack;

import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;

import de.osrg.base.util.Color;
import de.osrg.base.util.KeyValuePair;
import de.osrg.base.util.textformat.Applyable;
import de.osrg.base.util.textformat.ApplyableColor;
import de.osrg.base.util.textformat.Format;
import de.osrg.base.util.textformat.FormattableText;

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
