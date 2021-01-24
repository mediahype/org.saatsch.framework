package org.saatsch.framework.base.util.textformat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * represents a formattable text. Obtain instances from the
 * {@link FormattableTextFactory}
 * 
 * 
 * @author saatsch
 *
 */
public class FormattableText {

  private StringBuilder unformatted = new StringBuilder();

  private List<Format> formats = new ArrayList<Format>();

  /**
   * @return the List of Formats sorted by the offsets of the Formats in ascending order.
   */
  public List<Format> getFormats() {
    Collections.sort(formats);
    return formats;
  }


  /**
   * returns the text without formatting information.
   * 
   * @return the text without formatting information.
   */
  public String getUnformatted() {
    return unformatted.toString();
  }

  /**
   * inserts an {@link Insertable} into the unformatted text at the given
   * index.
   * 
   * @param index
   *            the index at which to insert the {@link Insertable}.
   * @return the instance.
   */
  public FormattableText insert(Insertable insert, int index) {

    if (index > unformatted.length()) {
      throw new IndexOutOfBoundsException("cannot insert after end");
    }

    formats.add(new Format(insert.getTag(), index));
    return this;
  }

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
  public FormattableText apply(Applyable apply, int beginIndex, int endIndex) {
    if (endIndex < beginIndex) {
      return this;
    }

    formats.add(new Format(apply.getEndTag(), endIndex));
    formats.add(new Format(apply.getStartTag(), beginIndex));

    return this;
  }

  /**
   * append unformatted text to the end of the buffer.
   * 
   * @param append
   *            the unformatted text to append.
   * @return itself.
   */
  public FormattableText append(String append) {
    unformatted.append(append);
    return this;
  }


}
