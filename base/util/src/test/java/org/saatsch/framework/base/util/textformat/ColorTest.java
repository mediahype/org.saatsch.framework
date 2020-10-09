package org.saatsch.framework.base.util.textformat;

import org.junit.Assert;
import org.junit.Test;

import org.saatsch.framework.base.util.Color;

public class ColorTest {

	@Test
	public void testColor() {
		Color c = new Color(230, 26, 122);
		Assert.assertEquals("#E61A7A", c.toHexString());
	}

}
