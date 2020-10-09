package org.saatsch.framework.jmmo.data.editor.ui.types.builtin;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.joda.beans.Bean;
import org.joda.beans.Property;

import org.saatsch.framework.jmmo.data.annotations.JmmoFloat;
import org.saatsch.framework.jmmo.data.api.PropertyUtil;
import org.saatsch.framework.jmmo.data.editor.ui.types.AbstractEditorComposite;

public class FloatEditor extends AbstractEditorComposite {
  private final Spinner spinner;
  private final Label lblRange;
  private JmmoFloat meta;

  public FloatEditor(Composite parent, Property<Object> property, Bean objectToEdit) {
    super(parent, property, SWT.NONE, objectToEdit);

    meta = property.metaProperty().annotation(JmmoFloat.class);

    spinner = new Spinner(this, SWT.BORDER);
    spinner.setBounds(10, 20, 73, 21);
    spinner.setMaximum(Integer.MAX_VALUE);
    spinner.setSelection(swtFloatToInt((Float) property.get(), meta.precision()));
    spinner.setDigits(meta.precision());


    lblRange = new Label(this, SWT.NONE);
    lblRange.setBounds(10, 46, 129, 13);

    spinner.addFocusListener(new FocusAdapter() {
      @Override
      public void focusLost(FocusEvent e) {

        property.set(swtIntToFloat(spinner.getSelection(), meta.precision()));
        saveObject();

      }
    });

    fillContents();

  }

  private void fillContents() {


    if (PropertyUtil.isPropertyAnnotatedWith(property, JmmoFloat.class)) {

      JmmoFloat meta = property.metaProperty().annotation(JmmoFloat.class);

      lblRange.setText(makeRangeString(meta));
      spinner.setMaximum(swtFloatToInt(meta.upperBound(), meta.precision()));
      spinner.setMinimum(swtFloatToInt(meta.lowerBound(), meta.precision()));

      spinner.setIncrement(swtFloatToInt(meta.increment(), meta.precision()));
    }


    spinner.pack();
    lblRange.pack();

  }

  private static Float swtIntToFloat(Integer in, int precision) {
    return new Float(in / Math.pow(10, precision));
  }

  private static int swtFloatToInt(Float number, int precision) {
    double mult = Math.pow(10, precision);
    return (int) Math.round(number * mult);
  }

  // private void round(Float ) {
  // double mult = Math.pow(10, precision);
  // double value = in / mult;
  // return new Float(Math.round(value * mult) / mult);
  //
  // }


  private static String makeRangeString(JmmoFloat meta) {

    String ret = String.valueOf(meta.lowerBound()) + " ... " + String.valueOf(meta.upperBound());
    return ret;
  }

}
