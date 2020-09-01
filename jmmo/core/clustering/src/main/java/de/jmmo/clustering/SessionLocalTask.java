package de.jmmo.clustering;

import java.util.Optional;

import org.apache.mina.core.future.WriteFuture;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.jmmo.cdi.container.JmmoContext;

/**
 * Socket connections are kept open all the time and a session only exists on the cluster member a
 * client initially connected to.
 *
 * Tasks that extend this class, typically directly react to a request object sent by the client and
 * only respond to the requesting client. Thus they will run on the same cluster member they where
 * created on.
 *
 * Call {@link #enqueue()} to enqueue a task for execution. Do not call {@link #enqueue()} on
 * yourself inside your own {@link #runTask()} method.
 *
 *
 * @author saatsch
 */
public abstract class SessionLocalTask extends TryCatchTask {

  @SuppressWarnings("unused")
  private static final Logger LOG = LoggerFactory.getLogger(SessionLocalTask.class);

  protected IoSession session;

  public SessionLocalTask(IoSession session) {
    this.session = session;
  }

  public ClusteringSupport getCluster() {
    return JmmoContext.getBean(ClusteringSupport.class);
  }

  /**
   * sets an attribute into the session or updates it.
   *
   * @param sessionAttributeKey the key of the attribute
   * @param value the value of the attribute
   * @return The old value of the attribute.  <tt>null</tt> if it is new.
   */
  protected Object setSessionAttribute(String sessionAttributeKey, String value) {
    return session.setAttribute(sessionAttributeKey, value);
  }

  /**
   * Returns the value of the user-defined attribute of this session.
   *
   * @param key the key of the attribute
   * @return <tt>null</tt> if there is no attribute with the specified key
   */
  protected String getSessionAttribute(String key) {
    return (String) session.getAttribute(key);
  }

  /**
   * @see #getSessionAttribute(String)
   */
  protected Optional<String> getSessionAttributeOptional(String key) {
    return Optional.ofNullable((String) session.getAttribute(key));
  }


  /**
   * enqueues this task in the (local) executor. Do not call this from within the Task execution
   * itself ({@link #runTask()}) as this would lead to a loop.
   */
  public void enqueue() {
    getCluster().executeLocally(this);
  }

  /**
   * writes the given Object to the session, transmitting it to the client.
   */
  public WriteFuture respond(Object o) {
    return session.write(o);
  }

  protected String getSessionId() {
    return String.valueOf(session.getId());
  }

}
