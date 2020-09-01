package de.osrg.base.swt.widgets;

import java.util.Arrays;

import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;

/**
 * a {@link Combo} that takes an enum class upon construction and fills itself with
 * the enum constants names (using the toString method) of the given enum.
 * 
 * @author saatsch
 *
 */
public class EnumCombo extends Combo {

	
	private Class<? extends Enum> optionsClass;

	public EnumCombo(Composite parent, Class<? extends Enum> e, int style) {
	  super(parent, style);
	  this.optionsClass = e;
	  
	  fillContents();
	  
	}

	private void fillContents() {
		if (null == optionsClass) {
			return;
		}

		Arrays.asList(optionsClass.getEnumConstants()).forEach(e -> add(e.toString()));

	}

	/**
	 * selects the given Enum value in the combobox.
	 * 
	 * @param e
	 */
	public void select(Enum<?> e) {
		select(indexOf(e.toString()));
	}

	/**
	 * returns the currently selected value as Enum.
	 * 
	 * @return the currently selected value as Enum.
	 */
	public Enum getSelected() {
	  return Enum.valueOf(optionsClass, getText());
	}

	@Override
	protected void checkSubclass() {
	}

}
