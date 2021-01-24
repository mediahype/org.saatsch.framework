package org.saatsch.framework.base.swt.textformat;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.saatsch.framework.base.swt.widgets.UpdateableComposite;
import org.saatsch.framework.base.util.textformat.FormattableText;
import org.saatsch.framework.base.util.textformat.renderer.FormattedTextRenderer;


/**
 * composite that displays detail about a FormattableTextImpl. While creating this, the weaknesses
 * of FormattableTextImpl become visible.
 * 
 * FormattableTextImpl does not retain all information about the formatting... etc.
 * 
 * @author saatsch
 *
 */
public class TextFormatC extends UpdateableComposite {

  private StyledText txtRaw;
  private StyledText txtFormatted;

  private FormattableText formattable = new FormattableText();
  private TextStyler styler = new TextStyler();
  private FormatsC cmpFormats;

  public TextFormatC(Composite parent) {
    super(parent, SWT.NONE);
    setLayout(new GridLayout(1, false));

    txtRaw = new StyledText(this, SWT.BORDER);
    txtRaw.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
    txtRaw.addKeyListener(new RawTextChanged(this));

    GridData this_gridData = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
    this.setLayoutData(this_gridData);

    txtFormatted = new StyledText(this, SWT.BORDER);
    txtFormatted.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
    txtFormatted.setEditable(false);

    cmpFormats = new FormatsC(this);


  }


  public void paintFormattedText() {
    styler.style(txtFormatted, formattable);
  }


  @Override
  public void updateYourself() {
    txtRaw.setText(FormattedTextRenderer.render(formattable));
    paintFormattedText();

    cmpFormats.setFormattableText(formattable);

  }

  public FormattableText getFormattedText() {
    return formattable;
  }

  public StyledText getStyledTextWidget() {
    return txtFormatted;
  }
  
}
