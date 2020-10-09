package org.saatsch.framework.base.simplescript.swt;

import java.util.ArrayList;
import java.util.List;

import org.saatsch.framework.base.simplescript.Simplescript;

public class ScriptsModel {

  private final List<Simplescript> scripts = new ArrayList<>();
  
  /**
   * @return the list of scripts, not <code>null</code>.
   */
  public List<Simplescript> getScripts() {
    return scripts;
  }
  
  public void addScript(String scriptName) {
    
    Simplescript script = new Simplescript();
    script.setName(scriptName);
    scripts.add(script);
    
  }

  
}
