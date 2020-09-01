package de.osrg.base.util.textformat;

/**
 * A Format is made up of an offset and a {@link Tag}. Formats are sorted by their offset.
 * 
 * @author saatsch
 *
 */
public class Format implements Comparable<Format> {

  /**
   * the offset of this format inside the text buffer
   */
  private Integer offset;

  private final Tag tag;

  public Format(Tag tag, int index) {
    offset = index;
    this.tag = tag;

  }

  public int getOffset() {
    return offset;
  }

  public void setOffset(int offset) {
    this.offset = offset;
  }

  public Tag getTag() {
    return tag;
  }

  @Override
  public int compareTo(Format o) {
    return offset.compareTo(o.getOffset());
  }

}
