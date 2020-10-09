package org.saatsch.framework.jmmo.basegame.common;

import java.io.Serializable;

/**
 * sent from client to server to request the creation of an account.
 * 
 * @author saatsch
 *
 */
public class CreateAccountRequest implements Serializable {

  private static final long serialVersionUID = 1L;
  private final String accountId;
  private final String accountPassword;


  public CreateAccountRequest(String accountName, String accountPass) {
    this.accountId = accountName;
    this.accountPassword = accountPass;
  }


  public String getAccountId() {
    return accountId;
  }


  public String getAccountPassword() {
    return accountPassword;
  }

}
