package org.saatsch.framework.base.jfxbase.demo.beantable;

import javafx.application.Application;
import org.junit.Test;
import org.saatsch.framework.base.jfxbase.BeanTable;
import org.saatsch.framework.base.jfxbase.control.VBox;
import org.saatsch.framework.base.jfxbase.demo.AbstractDemo;

public class BeanTableDemo extends AbstractDemo {
  @Override
  protected void fill(VBox root) {

    DemoBean demoBean = new DemoBean();
    demoBean.setSomeString("test String");
    demoBean.getNestedBean().getStringList().add("String 1");
    demoBean.getNestedBean().getStringList().add("String 2");


    BeanTable table = new BeanTable();
    table.setBean(demoBean);


    root.getChildren().add(table);
  }

  @Test
  public void test() {
    Application.launch(this.getClass());
  }


}
