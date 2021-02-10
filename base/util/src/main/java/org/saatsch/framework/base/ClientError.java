package org.saatsch.framework.base;

/**
 * equivalent of http 4XX return codes.
 * 
 * @author saatsch
 *
 */
public class ClientError extends RuntimeException {

  private static final long serialVersionUID = -1855552677309713002L;

  public ClientError() {
    super();
  }

  public ClientError(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  public ClientError(String message, Throwable cause) {
    super(message, cause);
  }

  public ClientError(String message) {
    super(message);
  }

  public ClientError(Throwable cause) {
    super(cause);
  }

  
  
}
