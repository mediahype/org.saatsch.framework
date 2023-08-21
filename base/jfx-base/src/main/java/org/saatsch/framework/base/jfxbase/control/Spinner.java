package org.saatsch.framework.base.jfxbase.control;

import javafx.collections.ObservableList;
import javafx.scene.control.SpinnerValueFactory;

public class Spinner<X> extends javafx.scene.control.Spinner<X> implements ExtendedNode<Spinner<X>> {

  public Spinner() {
  }

  public Spinner(int min, int max, int initialValue) {
    super(min, max, initialValue);
  }

  public Spinner(int min, int max, int initialValue, int amountToStepBy) {
    super(min, max, initialValue, amountToStepBy);
  }

  public Spinner(double min, double max, double initialValue) {
    super(min, max, initialValue);
  }

  public Spinner(double min, double max, double initialValue, double amountToStepBy) {
    super(min, max, initialValue, amountToStepBy);
  }

  public Spinner(ObservableList<X> items) {
    super(items);
  }

  public Spinner(SpinnerValueFactory<X> valueFactory) {
    super(valueFactory);
  }
}
