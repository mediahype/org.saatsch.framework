package org.saatsch.framework.jmmo.data.editor.fx;

/**
 * I dont know if we need this.
 * 
 * @author saatsch
 *
 */
public class DataEditorException extends RuntimeException {

  /**
   * 
   */
  private static final long serialVersionUID = -4549748973738886194L;

  public DataEditorException() {
    super();
  }

  public DataEditorException(String message, Throwable cause, boolean enableSuppression,
                             boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  public DataEditorException(String message, Throwable cause) {
    super(message, cause);
  }

  public DataEditorException(String message) {
    super(message);
  }

  public DataEditorException(Throwable cause) {
    super(cause);
  }

}
