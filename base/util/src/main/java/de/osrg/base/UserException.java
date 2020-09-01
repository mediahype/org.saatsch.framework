package de.osrg.base;

/**
 * Thrown when the end user of a client has to be shown a message that an error occured.
 * 
 * @author saatsch
 *
 */
public class UserException extends RuntimeException {

  private static final long serialVersionUID = 5964367784372180820L;

  public UserException() {
    super();

  }

  public UserException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);

  }

  public UserException(String message, Throwable cause) {
    super(message, cause);

  }

  public UserException(String message) {
    super(message);

  }

  public UserException(Throwable cause) {
    super(cause);

  }

  
  
}
