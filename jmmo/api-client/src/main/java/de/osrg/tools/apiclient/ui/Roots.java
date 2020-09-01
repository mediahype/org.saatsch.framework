package de.osrg.tools.apiclient.ui;

import java.util.List;

public class Roots {

  private List<Folder> children ;

  public Roots(List<Folder> children ){
    this.children = children;
  }
  
  
  public List<Folder> getChildren(){
    return children;
  }
  
}
