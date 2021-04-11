module saatsch.framework.jmmo.base.app.common {
  exports org.saatsch.framework.jmmo.basegame.common;
  exports org.saatsch.framework.jmmo.basegame.common.resources;
  exports org.saatsch.framework.jmmo.basegame.common.model;

  requires core;
  requires mongo.java.driver;
  requires org.joda.beans;
  requires saatsch.framework.jmmo.core.data;
}
