package org.saatsch.framework.base.util.textformat;

import org.junit.Assert;
import org.junit.Test;

import org.saatsch.framework.base.util.Color;
import org.saatsch.framework.base.util.textformat.renderer.FormattedTextRenderer;

public class FormattableTextTest {

	private static final String EXPECTED = "<c val=\"#E61A7A\">1</c><c val=\"#E61A7A\">2<n/></c>";

	@Test
	public void testFormattableText() {

		FormattableTextImpl text = new FormattableTextImpl();

		Newline newLine = new Newline();
		ApplyableColor c = new ApplyableColor(new Color(230, 26, 122));

		text.append("12").insert(newLine, 2);
		System.out.println(FormattedTextRenderer.render(text));

		text.apply(c, 0, 1);
		System.out.println(FormattedTextRenderer.render(text));
		text.apply(c, 1, 2);

		System.out.println(FormattedTextRenderer.render(text));

		System.out.println("expected: " + EXPECTED);
		Assert.assertEquals(EXPECTED, FormattedTextRenderer.render(text));

	}

}
