package de.jmmo.basegame.client;

import de.jmmo.basegame.common.CheckAccountRequest;
import de.jmmo.basegame.common.CheckNameResponse;
import de.jmmo.basegame.common.CreateAccountRequest;
import de.jmmo.basegame.common.CreateAccountResponse;
import de.jmmo.basegame.common.LoginRequest;
import de.jmmo.basegame.common.LogoutRequest;
import de.jmmo.basegame.common.Ping;
import de.jmmo.cdi.container.JmmoContext;

/**
 * bundles Network calls concerning connection, account and player character. All methods return
 * void as they work async.
 * 
 * @author saatsch
 *
 */

public class ServerConnectionApi {


  private ClientConnection client  = JmmoContext.getBean(ClientConnection.class);;

  /**
   * requests the server to create a new account. The server will answer with a
   * {@link CreateAccountResponse}.
   * 
   * @param account the account
   * @param pass the password
   */
  public void createAccount(String account, String pass) {
    if (!client.isConnected()) {
      client.connectSync();
    }
    client.sendTCP(new CreateAccountRequest(account, pass));
  }


  /**
   * checks if the given account name exists. The server returns a {@link CheckNameResponse}
   * 
   * @param selectedName the selected name for the account.
   */
  public void checkAccountName(String selectedName) {
    client.sendTCP(new CheckAccountRequest(selectedName));

  }

  /**
   * requests to login with an account and password. The server returns a LoginResponse.
   * 
   * @param account the account
   * @param pass the password
   */
  public void login(String account, String pass) {
    if (!client.isConnected()) {
      client.connectSync();
    }
    client.sendTCP(new LoginRequest(account, pass));

  }

  /**
   * requests to logout the current player character
   */
  public void logoutCharacter() {
    client.sendTCP(LogoutRequest.CHARACTER);
  }

  /**
   * requests to logout the account
   */
  public void logoutAccount() {
    client.sendTCP(LogoutRequest.ACCOUNT);
  }
  
  public boolean isConnected() {
    return client.isConnected();
  }
  
  public void disconnect() {
    client.disconnect();
  }
  
  public void ping() {
    if (!client.isConnected()) {
      client.connectSync();
    }
    client.sendTCP(new Ping(String.valueOf(System.currentTimeMillis())));
  }
  
}
