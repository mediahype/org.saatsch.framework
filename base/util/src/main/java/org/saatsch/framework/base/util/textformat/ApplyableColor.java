package org.saatsch.framework.base.util.textformat;

import org.saatsch.framework.base.util.Color;
import org.saatsch.framework.base.util.KeyValuePair;

public class ApplyableColor extends Applyable {

  public static final String COLOR_VALUE = "val";


  public ApplyableColor(Color c) {
    super(COLOR_TAG_NAME);

    if (null == c) {
      throw new IllegalArgumentException("color cannot be null");
    }
    getStartTag().getKeysValues().add(new KeyValuePair(COLOR_VALUE, c.toHexString()));


  }

}
