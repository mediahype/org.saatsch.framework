package de.jmmo.data;

public class DupKeyException extends RuntimeException {

  private static final long serialVersionUID = 2057741200026134542L;

  public DupKeyException() {
    super();
  }

  public DupKeyException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  public DupKeyException(String message, Throwable cause) {
    super(message, cause);
  }

  public DupKeyException(String message) {
    super(message);
  }

  public DupKeyException(Throwable cause) {
    super(cause);
  }

}
