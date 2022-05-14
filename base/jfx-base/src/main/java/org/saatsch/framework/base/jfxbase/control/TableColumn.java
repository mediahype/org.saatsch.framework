package org.saatsch.framework.base.jfxbase.control;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class TableColumn<S,T> extends javafx.scene.control.TableColumn<S,T> {

  public TableColumn(String name) {
    super(name);
  }

  public TableColumn<S, T> withSize(double value){
    setPrefWidth(value);
    return this;
  }

  /**
   * sets the CellValueFactory of this Column to a {@link PropertyValueFactory}
   *
   * @param propertyName the java bean property name.
   * @return this
   */
  public TableColumn<S,T> withPropertyValueFactory(String propertyName){
    setCellValueFactory(new PropertyValueFactory<S,T>(propertyName));
    return this;
  }

  public TableColumn<S,T> withCellValueFactory(Callback<CellDataFeatures<S, T>, ObservableValue<T>> param){
    setCellValueFactory(param);
    return this;
  }

  public TableColumn<S,T> withTable(TableView<S> table){
    table.getColumns().add(this);
    return this;
  }

}
