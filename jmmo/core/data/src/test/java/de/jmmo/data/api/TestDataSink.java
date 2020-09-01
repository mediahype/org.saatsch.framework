package de.jmmo.data.api;

import de.jmmo.data.mongo.MorphiaMongoDataSink;
import dev.morphia.Morphia;

public class TestDataSink extends MorphiaMongoDataSink {

  public TestDataSink() {
    super.init();
  }
  
  @Override
  protected void mapPackages(Morphia morphia) {

    morphia.mapPackage("de.jmmo.data.api.model");
    
  }

  @Override
  protected String dbName() {

    return "jmmo";
  }

  @Override
  protected String dbServer() {

    return "localhost";
  }

}
