package org.saatsch.framework.base.swt.textformat;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.List;
import org.saatsch.framework.base.util.textformat.FormattableText;

public class FormatsC extends Composite {

  private FormattableText formattable;
  private List lstFormats;

  public FormatsC(Composite parent) {
    super(parent, SWT.NONE);
    setLayout(new FillLayout(SWT.HORIZONTAL));
    
    SashForm sashForm = new SashForm(this, SWT.NONE);
    
    lstFormats = new List(sashForm, SWT.BORDER);
    
    Composite cmpFormat = new Composite(sashForm, SWT.NONE);
    sashForm.setWeights(new int[] {1, 1});

  }
  
  public void setFormattableText(FormattableText formattable) {
    this.formattable = formattable;
    repaint();
  }

  private void repaint() {
    lstFormats.removeAll();
    
    formattable.getFormats().forEach(format -> lstFormats.add(format.toString()));
    

  }
  
}
