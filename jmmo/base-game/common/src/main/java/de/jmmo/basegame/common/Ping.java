package de.jmmo.basegame.common;

import java.io.Serializable;

/**
 * Ping request.
 * 
 * @author saatsch
 *
 */
public class Ping implements Serializable{

  private final String id;
  
  public Ping(String id) {
    this.id = id;
  }
  
  public String getId() {
    return id;
  }
  
}
