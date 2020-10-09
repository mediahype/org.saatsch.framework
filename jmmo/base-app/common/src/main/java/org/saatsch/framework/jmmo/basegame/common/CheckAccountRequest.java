package org.saatsch.framework.jmmo.basegame.common;

import java.io.Serializable;

/**
 * sent by the client, to ask if a given account name already exists. The server must answer this
 * with a {@link SuccessAndReasonResponse} which must be successful, if the account does not exist.
 *
 * 
 * @author saatsch
 *
 */
public class CheckAccountRequest implements Serializable {

  private static final long serialVersionUID = 5633380572858509917L;

  private final String selectedName;

  public CheckAccountRequest(String selectedName) {
    this.selectedName = selectedName;
  }

  public String getSelectedName() {
    return selectedName;
  }

}
