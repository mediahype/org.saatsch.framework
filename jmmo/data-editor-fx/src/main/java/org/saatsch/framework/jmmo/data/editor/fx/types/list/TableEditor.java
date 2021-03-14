package org.saatsch.framework.jmmo.data.editor.fx.types.list;

import static org.saatsch.framework.jmmo.data.api.PropertyUtil.isPropertyAnnotatedWith;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.FlowPane;
import org.joda.beans.Bean;
import org.joda.beans.MetaBean;
import org.joda.beans.MetaProperty;
import org.joda.beans.Property;
import org.saatsch.framework.jmmo.data.annotations.JmmoEditorHidden;
import org.saatsch.framework.jmmo.data.api.Pointer;
import org.saatsch.framework.jmmo.data.editor.fx.types.EditorCreator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TableEditor extends AbstractListEditor<Object>{
  private static final Logger LOG = LoggerFactory.getLogger(TableEditor.class);

  FlowPane editorsPane = new FlowPane();

  public TableEditor(Property<Object> property,
      Bean objectToEdit) {
    super(property, objectToEdit);

    addColumns();
    repaintTable();

    getChildren().add(editorsPane);

    setPrefWidth(800);

  }


  private void addColumns() {
    MetaBean metaBean = null;
    try {
      metaBean = MetaBean.of(genericClass);
    }catch (IllegalArgumentException e) {
      LOG.error("cannot create editor for {}", property.name());
      return;
    }

    for (MetaProperty<?> m : metaBean.metaPropertyIterable()) {

      if (!isPropertyAnnotatedWith(m, JmmoEditorHidden.class)) {
        addColumn(m, 200);
      }

    }
  }

  /**
   * adds a column to the table.
   *  @param m
   * @param width
   */
  protected void addColumn(MetaProperty<?> m, int width) {

    TableColumn<Object, String> col = new TableColumn<>(m.name());



    col.setCellValueFactory(data -> {

      Object o = m.get((Bean) data.getValue());
      if (o instanceof Pointer){
        return new SimpleStringProperty(((Pointer<?>) o).asString());
      }

      return new SimpleStringProperty(o.toString());
    });
    tblTable.getColumns().add(col);

  }

  @Override
  protected void btnRemovePressed(ActionEvent event) {

  }

  @Override
  protected void btnAddPressed(ActionEvent event) {

  }

  @Override
  protected void repaintTable() {
    if (collectionToEdit.isEmpty()) return;

    tblTable.getItems().clear();

    for (Object bean : collectionToEdit) {
      tblTable.getItems().add(bean);
    }

  }

  @Override
  public void updateEditors(int selectionIndex) {


    drawEditors((Bean) tblTable.getSelectionModel().getSelectedItem());


  }

  private void drawEditors(Bean bean) {

    editorsPane.getChildren().clear();

    EditorCreator.createEditors( editorsPane, bean, objectToEdit);


  }


}
