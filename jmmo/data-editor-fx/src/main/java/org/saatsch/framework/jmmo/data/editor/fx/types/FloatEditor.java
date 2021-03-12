package org.saatsch.framework.jmmo.data.editor.fx.types;

import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import org.joda.beans.Bean;
import org.joda.beans.Property;
import org.saatsch.framework.jmmo.data.annotations.JmmoFloat;
import org.saatsch.framework.jmmo.data.api.PropertyUtil;

public class FloatEditor extends AbstractEditor {

  private final Spinner spinner;
  private final Label lblRange;
  private final JmmoFloat meta;

  public FloatEditor(Property<Object> property, Bean objectToEdit) {
    super(property, objectToEdit);

    meta = property.metaProperty().annotation(JmmoFloat.class);
    spinner = new Spinner();
    lblRange = new Label();

    getChildren().add(spinner);
    getChildren().add(lblRange);

    fillContents();

  }

  private void fillContents() {


    if (PropertyUtil.isPropertyAnnotatedWith(property, JmmoFloat.class)) {

      JmmoFloat meta = property.metaProperty().annotation(JmmoFloat.class);

      lblRange.setText(makeRangeString(meta));
//      spinner.setMaximum(swtFloatToInt(meta.upperBound(), meta.precision()));
//      spinner.setMinimum(swtFloatToInt(meta.lowerBound(), meta.precision()));
//
//      spinner.setIncrement(swtFloatToInt(meta.increment(), meta.precision()));
    }



  }

  private static Float intToFloat(Integer in, int precision) {
    return new Float(in / Math.pow(10, precision));
  }

  private static int floatToInt(Float number, int precision) {
    double mult = Math.pow(10, precision);
    return (int) Math.round(number * mult);
  }

  private static String makeRangeString(JmmoFloat meta) {

    String ret = meta.lowerBound() + " ... " + meta.upperBound();
    return ret;
  }
}
