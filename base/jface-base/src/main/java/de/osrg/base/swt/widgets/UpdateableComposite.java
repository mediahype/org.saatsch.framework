package de.osrg.base.swt.widgets;

import org.eclipse.swt.widgets.Composite;

/**
 * this is a composite that knows how to update itself.
 * 
 * @author saatsch
 *
 */
public abstract class UpdateableComposite extends Composite {

	public UpdateableComposite(Composite parent, int style) {
		super(parent, style);
	}
	/**
	 * called to tell this composite that it should update itself.
	 */
	public abstract void updateYourself();
	
}
