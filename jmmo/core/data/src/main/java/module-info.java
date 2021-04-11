module saatsch.framework.jmmo.core.data {
  exports org.saatsch.framework.jmmo.data;
  exports org.saatsch.framework.jmmo.data.api;
  exports org.saatsch.framework.jmmo.data.api.model;
  exports org.saatsch.framework.jmmo.data.config;
  exports org.saatsch.framework.jmmo.data.impl;
  exports org.saatsch.framework.jmmo.data.action;
  exports org.saatsch.framework.jmmo.data.mongo;
  exports org.saatsch.framework.jmmo.data.impl.visitor;
  exports org.saatsch.framework.jmmo.data.api.beans;

  requires commons.lang3;
  requires core;
  requires java.desktop;
  requires mongo.java.driver;
  requires org.joda.beans;
  requires org.slf4j;
  requires saatsch.framework.base.util;
  requires saatsch.framework.jmmo.core.cdi;
  requires saatsch.framework.jmmo.core.data.meta;
  requires saatsch.framework.base.serializing;
  requires java.sql;
  
  opens org.saatsch.framework.jmmo.data.mongo to core;
  opens org.saatsch.framework.jmmo.data.api.model to core;
  
}
