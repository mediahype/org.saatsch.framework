package org.saatsch.framework.base.util;

/**
 * because everybody needs a key value pair. This one is immutable.
 * 
 * @author saatsch
 *
 */
public class KeyValuePair {

  private final String key;
  private final Object value;

  /**
   * constructs a {@link KeyValuePair}
   * 
   * @param key the key. cannot be empty
   * @param value the value.
   * @throws IllegalArgumentException if the key is empty.
   */
  public KeyValuePair(String key, Object value) {
    super();
    if (null == key || key.length() == 0) {
      throw new IllegalArgumentException("key cannot be empty");
    }
    this.key = key;
    this.value = value;
  }

  public String getKey() {
    return key;
  }

  public Object getValue() {
    return value;
  }

}
