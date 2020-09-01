package de.osrg.base.serializing;

import java.util.ArrayList;
import java.util.List;

public class ObjectForSerializer {

  private String someString = "hello String";
  
  private List<String> someList = new ArrayList<>();
  
  public ObjectForSerializer() {
    someList.add("hello List");
  }

  public String getSomeString() {
    return someString;
  }

  public void setSomeString(String someString) {
    this.someString = someString;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((someList == null) ? 0 : someList.hashCode());
    result = prime * result + ((someString == null) ? 0 : someString.hashCode());
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
    ObjectForSerializer other = (ObjectForSerializer) obj;
    if (someList == null) {
      if (other.someList != null)
        return false;
    } else if (!someList.equals(other.someList))
      return false;
    if (someString == null) {
      if (other.someString != null)
        return false;
    } else if (!someString.equals(other.someString))
      return false;
    return true;
  }
  
  
  
}
