package org.saatsch.framework.base.util.textformat;

public class Insertable {

	private Tag tag;

	public Insertable(String tagName) {
		tag = new Tag(tagName, false, true);
	}

	public Tag getTag() {
		return tag;
	}

}
