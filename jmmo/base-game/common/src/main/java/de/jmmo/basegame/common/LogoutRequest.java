package de.jmmo.basegame.common;

import java.io.Serializable;

/**
 * 
 *
 */
public class LogoutRequest implements Serializable {

  public static final LogoutRequest CHARACTER = new LogoutRequest(LogoutType.CHARACTER);
  public static final LogoutRequest ACCOUNT = new LogoutRequest(LogoutType.ACCOUNT);
  
  private final LogoutType type;

  public LogoutRequest(LogoutType type) {
    this.type = type;
  }

  public LogoutType getType() {
    return type;
  }

  private static final long serialVersionUID = 2451352250045637340L;


  
  
}
