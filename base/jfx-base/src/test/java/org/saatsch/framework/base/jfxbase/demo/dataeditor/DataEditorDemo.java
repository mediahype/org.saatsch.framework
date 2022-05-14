package org.saatsch.framework.base.jfxbase.demo.dataeditor;

import javafx.application.Application;
import javafx.scene.paint.Color;
import jiconfont.icons.font_awesome.FontAwesome;
import jiconfont.javafx.IconFontFX;
import org.junit.Test;
import org.saatsch.framework.base.jfxbase.control.VBox;
import org.saatsch.framework.base.jfxbase.dataeditor.EditorCreator;
import org.saatsch.framework.base.jfxbase.demo.AbstractDemo;

public class DataEditorDemo extends AbstractDemo {

  @Override
  protected void fill(VBox root) {

    IconFontFX.register(FontAwesome.getIconFont());

    stage.getIcons().addAll(IconFontFX.buildImage(FontAwesome.PLUS_SQUARE, 16, Color.GREEN), IconFontFX.buildImage(FontAwesome.PLUS_SQUARE, 32, Color.GREEN));
    DemoBean bean = new DemoBean();
    bean.getList().add(new DemoInnerBean());
    EditorCreator.createEditors(root, bean, bean);


  }

  @Test
  public void test() {
    Application.launch(this.getClass());
  }
}
