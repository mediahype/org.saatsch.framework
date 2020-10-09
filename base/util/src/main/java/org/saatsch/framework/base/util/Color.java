package org.saatsch.framework.base.util;

/**
 * an RGB Color.
 * 
 * @author saatsch
 *
 */
public class Color {

  private final int r;
  private final int g;
  private final int b;

  public Color(int r, int g, int b) {
    this.r = r;
    this.g = g;
    this.b = b;
  }

  /**
   * convert this color to a Hex String
   * 
   * @return
   */
  public String toHexString() {
    return String.format("#%02X%02X%02X", getR(), getG(), getB());
  }

  /**
   * Creates a new instance of this class based on a given color hex string.
   * 
   * @param colorStr e.g. "#FFFFFF"
   * @return
   */
  public static Color fromHexString(String colorStr) {
    return new Color(Integer.valueOf(colorStr.substring(1, 3), 16),
        Integer.valueOf(colorStr.substring(3, 5), 16),
        Integer.valueOf(colorStr.substring(5, 7), 16));
  }

  public int getR() {
    return r;
  }

  public int getG() {
    return g;
  }

  public int getB() {
    return b;
  }

  @Override
  public int hashCode() {
    final int PRIME = 31;
    int result = 1;
    result = PRIME * result + b;
    result = PRIME * result + g;
    result = PRIME * result + r;
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Color other = (Color) obj;
    if (b != other.b)
      return false;
    if (g != other.g)
      return false;
    if (r != other.r)
      return false;
    return true;
  }

}
