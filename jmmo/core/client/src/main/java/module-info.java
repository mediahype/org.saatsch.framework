module saatsch.framework.jmmo.core.client {
  exports org.saatsch.framework.jmmo.client;

  requires guava;
  requires mina.core;
  requires org.slf4j;
  requires saatsch.framework.jmmo.core.cdi;
  requires saatsch.framework.jmmo.core.networking;
  requires saatsch.framework.base.util;
}
