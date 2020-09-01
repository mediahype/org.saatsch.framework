package de.osrg.tools.apiclient.ui.widgets;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

public class StringWidget extends Text implements ConvertableToFromObject<String> {

  public StringWidget(Composite parent, int style) {
    super(parent, style);
  }

  @Override
  public String asObject() {
    return getText();
  }

  @Override
  protected void checkSubclass() {
  }

  @Override
  public void fromObject(String o) {
    setText(o);
  }
  
}
