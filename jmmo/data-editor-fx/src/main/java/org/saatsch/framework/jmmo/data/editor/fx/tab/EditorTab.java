package org.saatsch.framework.jmmo.data.editor.fx.tab;


import org.joda.beans.Bean;

public interface EditorTab {

  /**
   * @return the Object class this Tab handles.
   */
  Class<? extends Bean> getObjectClass();


  void toggleEditMode();

}
