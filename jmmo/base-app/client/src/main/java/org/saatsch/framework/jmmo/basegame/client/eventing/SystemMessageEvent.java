package org.saatsch.framework.jmmo.basegame.client.eventing;

import org.saatsch.framework.jmmo.basegame.common.MessageSeverity;
import org.saatsch.framework.base.util.Assert;

/**
 * Posted when the user should receive a message from the system. The event carries a severity and
 * one or more messages.
 * 
 * @author saatsch
 *
 */
public class SystemMessageEvent {

  public static final MessageSeverity DEFAULT_SEVERITY = MessageSeverity.INFO;

  private final String[] message;

  private final MessageSeverity severity;

  /**
   * constructs a new SystemMessageEvent with the default severity.
   * 
   * @param message the payload message(s).
   */
  public SystemMessageEvent(String... message) {
    Assert.notNull(message);
    this.message = message;
    this.severity = DEFAULT_SEVERITY;
  }

  public SystemMessageEvent(MessageSeverity severity, String... message) {
    this.message = message;
    this.severity = severity;

  }

  /**
   * @return the array of payload messages. Never <code>null</code>
   */
  public String[] getMessage() {
    return message;
  }

  public MessageSeverity getSeverity() {
    return severity;
  }



}
