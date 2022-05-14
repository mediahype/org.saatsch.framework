package org.saatsch.framework.base.jfxbase.control;

import javafx.beans.value.ChangeListener;

public class TableView<S> extends javafx.scene.control.TableView<S> implements ExtendedNode<TableView<S>> {

  public TableView<S> withSelectionChangedListener(ChangeListener<S> listener){
    getSelectionModel().selectedItemProperty().addListener(listener);
    return this;
  }

}
