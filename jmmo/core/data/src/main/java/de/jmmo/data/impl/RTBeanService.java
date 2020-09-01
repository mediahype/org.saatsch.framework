package de.jmmo.data.impl;

import org.joda.beans.Bean;

import de.jmmo.data.impl.visitor.DepthDeleter;

public class RTBeanService {

  public void depthDelete(Bean bean) {
    DepthDeleter d = new DepthDeleter();
    d.visit(bean);
  }

}
