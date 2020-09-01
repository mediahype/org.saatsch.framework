package de.osrg.tools.apiclient.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Folder {

  private final String name;
  
  private final List<AbstractMethod> methods = new ArrayList<>();
  
  public Folder(String name) {
    this.name = name;
  }

  public List<AbstractMethod> getTasks(){
    return methods;
  }

  public void addMethod(AbstractMethod t){
    methods.add(t);
    Collections.sort(methods, (o1, o2) -> o1.getName().compareTo(o2.getName()));
  }
  
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
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
    Folder other = (Folder) obj;
    if (getName() == null) {
      if (other.getName() != null)
        return false;
    } else if (!getName().equals(other.getName()))
      return false;
    return true;
  }

  public String getName() {
    return name;
  }
  
}
