package org.saatsch.framework.base.jfxbase;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javafx.scene.layout.VBox;

/**
 * a caching loader.
 * 
 * @author saatsch
 *
 */
public class JfxLoader {

  private Map<Class, VBox> cache = Collections.synchronizedMap(new HashMap<>());
  
  public JfxLoader() {
  }
  
  
  /**
   * load a component via FXMLLoader. Naming rules apply. The given component must be a VBox.
   * the loaded component will be cached and returned by subsequent calls.
   * 
   * @param componentClass
   * @return
   */
  public synchronized VBox load(Class<? extends VBox> componentClass) {
    
    if (cache.containsKey(componentClass)) {
      return cache.get(componentClass);
    }
    
    VBox load = JfxUtil.load(componentClass);
    cache.put(componentClass, load);
    return load;
  }
  
  
}
