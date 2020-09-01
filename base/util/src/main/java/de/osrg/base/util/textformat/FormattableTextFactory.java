package de.osrg.base.util.textformat;

import de.osrg.base.util.Color;

/**
 * 
 * 
 * @author saatsch
 *
 */
public class FormattableTextFactory {

	public static FormattableText createNewText() {
		return new FormattableTextImpl();
	}

	public static ApplyableColor createNewColor(int r, int g, int b) {
		return new ApplyableColor(new Color(r, g, b));
	}

}
