package org.saatsch.framework.jmmo.data;

/**
 * generic exception thrown by the data layer.
 * 
 * @author saatsch
 *
 */
public class DataException extends RuntimeException {

  public DataException() {
    super();
  }

  public DataException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  public DataException(String message, Throwable cause) {
    super(message, cause);
  }

  public DataException(String message) {
    super(message);
  }

  public DataException(Throwable cause) {
    super(cause);
  }

  
  
}
