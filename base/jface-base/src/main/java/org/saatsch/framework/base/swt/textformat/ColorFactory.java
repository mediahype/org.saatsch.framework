package org.saatsch.framework.base.swt.textformat;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

/**
 * translates between swt and osrg color
 * 
 * @author saatsch
 *
 */
public class ColorFactory {

	private static final class InstanceHolder {
		static final ColorFactory INSTANCE = new ColorFactory();
	}

	public static ColorFactory getInstance() {
		return InstanceHolder.INSTANCE;
	}

	public Color getColor(org.saatsch.framework.base.util.Color color) {
		return new Color(Display.getCurrent(), color.getR(), color.getG(),
				color.getB());
	}

}
