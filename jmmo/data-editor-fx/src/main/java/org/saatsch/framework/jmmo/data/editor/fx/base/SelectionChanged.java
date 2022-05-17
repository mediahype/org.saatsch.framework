package org.saatsch.framework.jmmo.data.editor.fx.base;

public interface SelectionChanged<T> {

  /**
   * presents a way for the left Pane in the Tab to tell something else that the selection has changed.
   *
   * @param newSelection the new selection
   */
  void selectionChanged(T newSelection);



}
