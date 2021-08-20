package org.saatsch.framework.base.jfxbase.widget;

import javafx.scene.paint.Color;
import jiconfont.icons.font_awesome.FontAwesome;
import jiconfont.javafx.IconNode;
import org.saatsch.framework.base.jfxbase.control.Label;

public class InfoLabel extends Label {

  public InfoLabel(Color color) {
    super();

    IconNode iconNode = new IconNode(FontAwesome.INFO_CIRCLE);
    iconNode.setIconSize(16);
    iconNode.setFill(color);

    setGraphic(iconNode);

  }

}
