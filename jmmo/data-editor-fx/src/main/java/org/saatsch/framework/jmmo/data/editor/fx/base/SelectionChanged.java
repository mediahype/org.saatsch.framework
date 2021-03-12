package org.saatsch.framework.jmmo.data.editor.fx.base;

import org.joda.beans.Bean;

public interface SelectionChanged<T> {

  void selectionChanged(T newSelection);
}
