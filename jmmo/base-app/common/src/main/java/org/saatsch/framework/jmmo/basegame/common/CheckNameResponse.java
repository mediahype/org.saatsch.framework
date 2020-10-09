package org.saatsch.framework.jmmo.basegame.common;

/**
 * sent in response to a {@link CheckAccountRequest}.
 * 
 * @author saatsch
 *
 */
public class CheckNameResponse extends SuccessAndReasonResponse {

  private static final long serialVersionUID = 1612577557244483509L;

  public CheckNameResponse(boolean success, String reason) {
    super(success, reason);
  }

}
