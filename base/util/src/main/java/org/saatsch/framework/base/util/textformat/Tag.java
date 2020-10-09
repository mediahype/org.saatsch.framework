package org.saatsch.framework.base.util.textformat;

/**
 * 
 * 
 * @author saatsch
 *
 */
public class Tag extends KeyValueSupport {

	private String tagName;

	/**
	 * if it is part of a surrounding tag. If <code>false</code> then
	 * {@link #isStart()} must be true;
	 */
	private final boolean surrounding;

	/**
	 * if it is a starting tag.
	 */
	private final boolean start;

	public Tag(String tagName, boolean surrounding, boolean start) {
		this.tagName = tagName;
		this.surrounding = surrounding;
		this.start = start;

		if (start == false && surrounding == false) {
			throw new IllegalArgumentException(
					"if surrounding is false, start must be true.");
		}

	}

	public String getName() {
		return tagName;
	}

	public boolean isSurrounding() {
		return surrounding;
	}

	public boolean isStart() {
		return start;
	}

}
