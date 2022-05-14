package org.saatsch.framework.jmmo.data.mongo;

import org.saatsch.framework.jmmo.data.DataSink;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dev.morphia.Morphia;

public class NoopDataSink extends DataSink {

  private static final Logger LOG = LoggerFactory.getLogger(ModelValidator.class);

  @Override
  protected void mapPackages(Morphia morphia) {

  }


  @Override
  protected String dbName() {
    return null;
  }

  @Override
  protected String dbServer() {
    return null;
  }

}
