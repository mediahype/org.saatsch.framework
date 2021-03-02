package org.saatsch.framework.jmmo.networking.api.vo;

import java.io.Serializable;

/**
 * sent from server to client to inform the client of an error on the server. This might later be
 * deleted, when we want to send dedicated error messages to the client instead of a raw
 * message.
 * 
 * @author saatsch
 *
 */
public class ServerError implements Serializable {

  private static final long serialVersionUID = -2311375829969528991L;

  private final String message;

  private final Exception cause;

  public ServerError(String message) {
    this.message = message;
    this.cause = null;
  }

  public ServerError(String message, Exception cause) {
    this.message = message;
    this.cause = cause;
  }
  
  public String getMessage() {
    return message;
  }

  public Exception getCause() {
    return cause;
  }



}
