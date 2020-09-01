package de.osrg.base.util;

import java.util.Collection;

public class Assert {

  private Assert() {}

  /**
   * throws an {@link IllegalArgumentException} if the given input is <code>null</code>.
   * 
   * @param input the input
   */
  public static void notNull(Object input) {
    if (null == input) {
      throw new IllegalArgumentException("Argument must not be null");
    }
  }

  /**
   * throws an {@link IllegalArgumentException} using the given message if the given input is <code>null</code>.
   * 
   * @param input the input
   * @param message the message
   */
  public static void notNull(Object input, String message) {
    if (null == input) {
      throw new IllegalArgumentException(message);
    }
  }

  /**
   * asserts that the given collection is empty.
   * 
   * @param input
   */
  public static void emptyCollection(Collection input) {
    if (!input.isEmpty()) {
      throw new IllegalArgumentException("Input must be an empty Collection.");
    }
  }
  
}
