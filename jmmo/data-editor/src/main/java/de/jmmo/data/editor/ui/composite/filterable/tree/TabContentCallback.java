package de.jmmo.data.editor.ui.composite.filterable.tree;

import org.joda.beans.Bean;

public interface TabContentCallback {

  Class getObjectClass();

  Object getCurrentlySelected();

  void contentDisposeChildren();

  /**
   * this is called when the object selection has changed.
   * 
   * @param bean
   */
  void repaintContent(Bean bean);

}
