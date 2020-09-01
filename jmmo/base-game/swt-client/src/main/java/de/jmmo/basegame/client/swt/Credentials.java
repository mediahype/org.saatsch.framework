package de.jmmo.basegame.client.swt;

public class Credentials {

  private final String user;
  private final String pass;


  public Credentials(String user, String pass) {
    super();
    this.user = user;
    this.pass = pass;
  }


  public String getUser() {
    return user;
  }


  public String getPass() {
    return pass;
  }

}
