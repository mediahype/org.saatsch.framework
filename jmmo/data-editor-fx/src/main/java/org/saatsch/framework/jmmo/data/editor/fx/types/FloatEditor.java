package org.saatsch.framework.jmmo.data.editor.fx.types;

import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory.DoubleSpinnerValueFactory;
import org.joda.beans.Bean;
import org.joda.beans.Property;
import org.saatsch.framework.jmmo.data.annotations.JmmoFloat;
import org.saatsch.framework.jmmo.data.api.PropertyUtil;

/**
 * TODO: set precision
 */
public class FloatEditor extends AbstractEditor {

  private final Spinner spinner;
  private final Label lblRange;

  // TODO: use this in setting the precision
  private final JmmoFloat meta;

  public FloatEditor(Property<Object> property, Bean objectToEdit) {
    super(property, objectToEdit);

    meta = property.metaProperty().annotation(JmmoFloat.class);
    spinner = new Spinner();
    spinner.setEditable(true);
    lblRange = new Label();

    getChildren().add(spinner);
    getChildren().add(lblRange);

    fillContents();

  }

  private void fillContents() {

    if (PropertyUtil.isPropertyAnnotatedWith(property, JmmoFloat.class)) {

      JmmoFloat meta = property.metaProperty().annotation(JmmoFloat.class);

      DoubleSpinnerValueFactory factory = new DoubleSpinnerValueFactory(
          meta.lowerBound(), meta.upperBound(), (Float) property.get(), meta.increment());

      spinner.setValueFactory(factory);


      lblRange.setText(makeRangeString(meta));
    }

    spinner.valueProperty().addListener((observable, oldValue, newValue) -> {
      property.set(newValue);
      saveObject();
    });

  }


  private static String makeRangeString(JmmoFloat meta) {

    String ret = meta.lowerBound() + " ... " + meta.upperBound();
    return ret;
  }
}
