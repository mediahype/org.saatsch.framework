package org.saatsch.framework.base.util.textformat;

import org.saatsch.framework.base.util.Color;
import org.saatsch.framework.base.util.KeyValuePair;

public class ApplyableColor extends Applyable {

  private static final String COLOR = "color";

  public static final String COLOR_VALUE = "val";


  public ApplyableColor(Color c) {
    super(COLOR_TAG_NAME);

    if (null == c) {
      throw new IllegalArgumentException("color cannot be null");
    }
    getStartTag().getKeysValues().add(new KeyValuePair("val", c.toHexString()));
    getStartTag().getData().put(COLOR, c);

  }

}
