package org.saatsch.framework.base.util.textformat;

/**
 * an Applyable can be applied to a {@link FormattableText}
 * 
 * @author saatsch
 *
 */
public class Applyable {

  public static final String COLOR_TAG_NAME = "c";

  public Applyable(String tagName) {
    startTag = new Tag(tagName, true, true);
    endTag = new Tag(tagName, true, false);
  }

  private Tag endTag;
  private Tag startTag;

  Tag getEndTag() {
    return endTag;
  }

  Tag getStartTag() {
    return startTag;
  }

}
