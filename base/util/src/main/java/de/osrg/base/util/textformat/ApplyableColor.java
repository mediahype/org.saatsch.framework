package de.osrg.base.util.textformat;

import de.osrg.base.util.Color;
import de.osrg.base.util.KeyValuePair;

public class ApplyableColor extends Applyable {

  private static final String COLOR = "color";

  public static final String COLOR_VALUE = "val";


  public ApplyableColor(Color c) {
    super(Applyable.COLOR_TAG_NAME);

    if (null == c) {
      throw new IllegalArgumentException("color cannot be null");
    }
    getStartTag().getKeysValues().add(new KeyValuePair("val", c.toHexString()));
    getStartTag().getData().put(COLOR, c);

  }

}
