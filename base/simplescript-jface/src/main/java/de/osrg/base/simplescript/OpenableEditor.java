package de.osrg.base.simplescript;

import de.osrg.base.swt.Openable;

public interface OpenableEditor<T> extends Openable<T> {

  void setToEdit(T step);

}
