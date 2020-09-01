package de.osrg.base.simplescript;

/**
 * thrown, when an unrecoverable exception in a script, or a situation in which the execution must
 * be stopped, has occured.
 * 
 * @author saatsch
 *
 */
public class ExecutionException extends Exception{

  private static final long serialVersionUID = -3484104124034204323L;

  public ExecutionException() {
    super();
  }

  public ExecutionException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  public ExecutionException(String message, Throwable cause) {
    super(message, cause);
  }

  public ExecutionException(String message) {
    super(message);
  }

  public ExecutionException(Throwable cause) {
    super(cause);
  }

  
  
}
