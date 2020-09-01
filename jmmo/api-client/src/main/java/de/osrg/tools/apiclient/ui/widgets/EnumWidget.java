package de.osrg.tools.apiclient.ui.widgets;

import org.eclipse.swt.widgets.Composite;

import de.osrg.base.swt.widgets.EnumCombo;

public class EnumWidget extends EnumCombo implements ConvertableToFromObject<Enum<?>> {

  public EnumWidget(Composite parent, Class<? extends Enum<?>> e, int style) {
    super(parent, e, style);

  }

  @Override
  public Enum<?> asObject() {
    return getSelected();
  }

  @Override
  public void fromObject(Enum<?> o) {
    select( o);
    
  }



}
