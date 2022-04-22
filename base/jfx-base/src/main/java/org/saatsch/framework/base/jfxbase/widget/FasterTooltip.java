package org.saatsch.framework.base.jfxbase.widget;

import javafx.scene.control.Tooltip;
import javafx.util.Duration;

/**
 * A tooltip that appears faster than the JFX default, which is really slow. Default appearance time for this tooltip is
 * 400ms but this can be customized.
 */
public class FasterTooltip extends Tooltip {

  public FasterTooltip(String text) {
    super(text);
    setShowDelay(Duration.millis(400));
  }

  public FasterTooltip() {
    super();
    setShowDelay(Duration.millis(400));
  }

  public FasterTooltip(String text, double millis) {
    super(text);
    setShowDelay(Duration.millis(millis));
  }


}
