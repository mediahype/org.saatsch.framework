package org.saatsch.framework.base.swt;

/**
 * this was introduced with regards to testing purposes. It is the contract, that any Openable is also a {@link
 * org.eclipse.swt.widgets.Dialog} and that the {@link #open()} method blocks until the dialog is closed.
 *
 * @param <T> the type that is returned by the {@link #open()} method.
 *
 */

public interface Openable<T> {

  /**
   * open the {@link org.eclipse.swt.widgets.Dialog}. Block until the Dialog is closed.
   * 
   * @return an arbitrary result.
   */
  T open();

}
