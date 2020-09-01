package de.osrg.tools.apiclient.ui.widgets;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Spinner;

public class IntegerWidget extends Spinner implements ConvertableToFromObject<Integer> {

  public IntegerWidget(Composite parent, int style) {
    super(parent, style);
    setSelection(0);
  }

  @Override
  public Integer asObject() {
    return getSelection();
  }

  @Override
  protected void checkSubclass() {
  }

  @Override
  public void fromObject(Integer o) {
    setSelection(o);
  }
  
}
