package de.jmmo.clustering.session;

import java.io.Serializable;

public class UserSession implements Serializable {

  private static final long serialVersionUID = -8909339378605966589L;
  
  /**
   * the UUID of the cluster member to which the player's client is connected by network. This is
   * helpful when a message should be sent to the player's client which is not a direct response to
   * a request. In this case we can enqueue a task on the cluster member identified by this UUID and
   * be sure that the task finds the network connection to send the message on.
   */
  private final String clusterMemberUUID;
  
  public UserSession(String clusterMemberUUID) {
    this.clusterMemberUUID = clusterMemberUUID;
  }
  
  public String getClusterMemberUUID() {
    return clusterMemberUUID;
  }
  
}
