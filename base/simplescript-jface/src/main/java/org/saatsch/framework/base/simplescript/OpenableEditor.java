package org.saatsch.framework.base.simplescript;

import org.saatsch.framework.base.swt.Openable;

public interface OpenableEditor<T> extends Openable<T> {

  void setToEdit(T step);

}
