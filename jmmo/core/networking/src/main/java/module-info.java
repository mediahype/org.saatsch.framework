module saatsch.framework.jmmo.core.networking {
  
  requires org.slf4j;
  requires mina.core;
  requires saatsch.framework.jmmo.core.cdi;
  requires saatsch.framework.base.beans;
  requires org.joda.beans;
  requires saatsch.framework.base.classpath.support;
  requires guava;
  requires reflections8;
  requires saatsch.framework.base.util;
  requires saatsch.framework.base.serializing;

  exports org.saatsch.framework.jmmo.networking.api;
  exports org.saatsch.framework.jmmo.networking.beansync;
  exports org.saatsch.framework.jmmo.networking.impl;
  exports org.saatsch.framework.jmmo.networking.impl.mina;
}