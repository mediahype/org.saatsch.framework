module saatsch.framework.jmmo.baseapp.client {
  exports org.saatsch.framework.jmmo.basegame.client.boot;
  exports org.saatsch.framework.jmmo.basegame.client.handlers;
  exports org.saatsch.framework.jmmo.basegame.client.eventing;
  exports org.saatsch.framework.jmmo.basegame.client;

  requires guava;
  requires java.desktop;
  requires javax.el.api;
  requires juel.impl;
  requires mina.core;
  requires org.joda.beans;
  requires org.slf4j;
  requires saatsch.framework.base.beans;
  requires saatsch.framework.base.expression.base;
  requires saatsch.framework.base.util;
  requires saatsch.framework.jmmo.base.app.common;
  requires saatsch.framework.jmmo.core.cdi;
  requires saatsch.framework.jmmo.core.client;
  requires saatsch.framework.jmmo.core.data;
  requires saatsch.framework.jmmo.core.networking;
}
