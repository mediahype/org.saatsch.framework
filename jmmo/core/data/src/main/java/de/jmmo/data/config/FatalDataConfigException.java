package de.jmmo.data.config;

/**
 * thrown when a fatal exception occured while loading the data module configuration and the module
 * cannot continue to work.
 * 
 * @author saatsch
 *
 */
public class FatalDataConfigException extends RuntimeException {

  /**
   * 
   */
  private static final long serialVersionUID = -4549748973738886194L;

  public FatalDataConfigException() {
    super();
  }

  public FatalDataConfigException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  public FatalDataConfigException(String message, Throwable cause) {
    super(message, cause);
  }

  public FatalDataConfigException(String message) {
    super(message);
  }

  public FatalDataConfigException(Throwable cause) {
    super(cause);
  }

}
