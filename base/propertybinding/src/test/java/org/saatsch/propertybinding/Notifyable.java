package org.saatsch.propertybinding;

public class Notifyable {

  private int notifications = 0;

  public void notifyMe(){
    notifications++;
  }

  public boolean wasNotifiedOnce(){
    return notifications == 1;
  }

  public int notifications(){
    return notifications;
  }

}
