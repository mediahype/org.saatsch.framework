package org.saatsch.framework.jmmo.data.editor.fx.beantree.cat;

import org.joda.beans.Bean;

public class CatOrBean {

  private final Bean bean;
  private final Object cat;

  public CatOrBean(Bean bean) {
    this.bean = bean;
    this.cat = null;
  }

  // TODO: what type is object?
  public CatOrBean(Object cat) {
    this.cat = cat;
    this.bean = null;
  }
}
