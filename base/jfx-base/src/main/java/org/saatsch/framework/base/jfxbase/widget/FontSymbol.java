package org.saatsch.framework.base.jfxbase.widget;

import javafx.scene.paint.Paint;
import jiconfont.IconCode;
import jiconfont.javafx.IconNode;
import org.saatsch.framework.base.jfxbase.control.ExtendedNode;

public class FontSymbol extends IconNode implements ExtendedNode<FontSymbol> {

  public FontSymbol(IconCode iconCode, Number iconSize, Paint color) {
    super(iconCode);
    setIconSize(iconSize);
    setFill(color);
  }
}
