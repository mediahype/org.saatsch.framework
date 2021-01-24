package org.saatsch.framework.base.util.textformat;

import org.saatsch.framework.base.util.Color;

/**
 * 
 * 
 * @author saatsch
 *
 */
public class FormattableTextFactory {

	public static FormattableText createNewText() {
		return new FormattableText();
	}

	public static ApplyableColor createNewColor(int r, int g, int b) {
		return new ApplyableColor(new Color(r, g, b));
	}

}
