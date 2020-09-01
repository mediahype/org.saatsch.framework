package de.osrg.base.util.textformat.renderer;

import de.osrg.base.util.KeyValuePair;
import de.osrg.base.util.textformat.Format;
import de.osrg.base.util.textformat.FormattableText;
import de.osrg.base.util.textformat.Tag;

/**
 * renders a {@link FormattableText} into a String using xml style.
 * 
 * @author saatsch
 *
 */
public class FormattedTextRenderer {

	public static String render(FormattableText fText) {
 
		StringBuilder builder = new StringBuilder();
		builder.append(fText.getUnformatted());

		Integer appliedLen = 0;
		
		for (Format format : fText.getFormats()) {
			
			String toInsert = renderTag(format.getTag());
			builder.insert(format.getOffset() + appliedLen, toInsert );
			appliedLen = appliedLen + toInsert.length();
		}

		
		
		return builder.toString();
		
	}

	private static String renderTag(Tag tag) {
		if (tag.isStart())
		{
			return renderStartTag(tag);
		}else{
			return renderEndTag(tag);
		}
	}
	
	
	private static String renderEndTag(Tag tag) {
		if (tag.isSurrounding()) {
			return "</" + tag.getName()+ ">";
		}
		return "";
	}

	private static String renderStartTag(Tag tag) {

		String ret = "<" + tag.getName();
		for (KeyValuePair kvp : tag.getKeysValues()) {
			ret = ret + " " + kvp.getKey() + "=\"" + kvp.getValue() + "\"";
		}
		if (!tag.isSurrounding()) {
			ret = ret + "/";
		}
		ret = ret + ">";
		return ret;

		
		
	}

}
