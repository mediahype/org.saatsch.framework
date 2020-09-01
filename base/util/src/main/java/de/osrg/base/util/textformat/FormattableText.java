package de.osrg.base.util.textformat;

import java.util.List;

/**
 * represents a formattable text. Obtain instances from the
 * {@link FormattableTextFactory}
 * 
 * 
 * @author saatsch
 *
 */
public interface FormattableText {

	/**
	 * returns the text without formatting information.
	 * 
	 * @return the text without formatting information.
	 */
	String getUnformatted();

	/**
	 * @return the List of Formats sorted by the offsets of the Formats in ascending order.
	 */
	List<Format> getFormats();

	/**
	 * inserts an {@link Insertable} into the unformatted text at the given
	 * index.
	 * 
	 * @param index
	 *            the index at which to insert the {@link Insertable}.
	 * @return the instance.
	 */
	FormattableText insert(Insertable insert, int index);

	/**
	 * applies an {@link Applyable} to the unformatted text from a given beginIndex to a
	 * given endIndex.
	 * 
	 * @param apply
	 *            the {@link Applyable} to apply
	 * @param beginIndex
	 *            the beginIndex into the unformatted text
	 * @param endIndex
	 *            the endIndex into the unformatted text
	 * @return the instance.
	 */
	FormattableText apply(Applyable apply, int beginIndex, int endIndex);

	/**
	 * append unformatted text to the end of the buffer.
	 * 
	 * @param append
	 *            the unformatted text to append.
	 * @return itself.
	 */
	FormattableText append(String append);

	/**
	 * insert unformatted text at a position into the buffer
	 * 
	 * @param toInsert
	 * @param atIndex
	 * @return itself.
	 */
	FormattableText insert(String toInsert, int atIndex);

}
