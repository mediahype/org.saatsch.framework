package org.saatsch.framework.base.jfxbase.control;

public class ComboBox<T> extends javafx.scene.control.ComboBox<T> implements ExtendedNode<ComboBox<T>> {

  /**
   * adds the given items to the list of items.
   *
   * @param items the items to add.
   * @return
   */
  public ComboBox<T> withItems(T... items){
    getItems().addAll(items);
    return this;
  }

}
