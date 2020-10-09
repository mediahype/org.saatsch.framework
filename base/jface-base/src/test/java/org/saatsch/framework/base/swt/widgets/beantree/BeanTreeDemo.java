package org.saatsch.framework.base.swt.widgets.beantree;

import java.util.ArrayList;
import java.util.List;

import org.saatsch.framework.base.jface.beantree.BeanTree;
import org.saatsch.framework.base.swt.AbstractDemo;

public class BeanTreeDemo extends AbstractDemo {

  public static void main(String[] args) {
    
    BeanTreeDemo b = new BeanTreeDemo();
    b.open();
    
  }

  @Override
  protected void createContents() {

    DemoBean demoBean = new DemoBean();
    DemoBean demoBean2 = new DemoBean();
    
    List<Object> beans = new ArrayList<>();
    beans.add(demoBean);
    beans.add(demoBean2);
    
    demoBean.getNestedBean().getStringList().add("StringContent");
    demoBean.getNestedBean().getBeanList().add(new DemoNestedBean());
    
    BeanTree bt = new BeanTree(content);
    bt.get().setInput(beans);
    
    
  }
  
}
