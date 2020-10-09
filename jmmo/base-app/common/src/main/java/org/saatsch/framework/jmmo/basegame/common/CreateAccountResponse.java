package org.saatsch.framework.jmmo.basegame.common;

import java.io.Serializable;

public class CreateAccountResponse extends SuccessAndReasonResponse implements Serializable {

  private static final long serialVersionUID = 1L;

  public CreateAccountResponse(boolean success, String reason) {
    super(success, reason);
  }

}
