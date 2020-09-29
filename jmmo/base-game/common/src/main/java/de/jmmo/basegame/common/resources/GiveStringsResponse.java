package de.jmmo.basegame.common.resources;

import java.io.Serializable;
import java.util.List;

import de.jmmo.data.api.model.IntlString;

public class GiveStringsResponse implements Serializable {

  private static final long serialVersionUID = 4445174139724367347L;

  /**
   * the list of all existing {@link IntlString}s.
   */
  private final List<IntlString> list;
  
  
  public GiveStringsResponse(List<IntlString> list) {
    this.list = list;
  }
  
  public boolean add(IntlString toAdd) {
    return list.add(toAdd);
  }
  
  public List<IntlString> getList() {
    return list;
  }
  
}
