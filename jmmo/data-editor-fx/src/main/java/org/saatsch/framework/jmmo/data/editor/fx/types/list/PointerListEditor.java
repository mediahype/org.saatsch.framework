package org.saatsch.framework.jmmo.data.editor.fx.types.list;

import java.util.Collection;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import org.joda.beans.Bean;
import org.joda.beans.Property;
import org.saatsch.framework.jmmo.data.api.Pointer;

public class PointerListEditor extends AbstractListEditor<Pointer<?>> {


  public PointerListEditor(Property<Object> property,
      Bean objectToEdit) {
    super(property, objectToEdit);

    TableColumn<Pointer<?>, String> nameColumn = new TableColumn<>("Name");
    nameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().asString()));
    tblTable.getColumns().add(nameColumn);

    repaintTable();

  }

  @Override
  protected void btnRemovePressed(ActionEvent event) {
    if (getSelection() == null) {
      return;
    }
    collectionToEdit.remove(getSelection());
    repaintTable();
    saveObject();
  }

  @Override
  protected void btnAddPressed(ActionEvent event) {

  }

  @Override
  protected void repaintTable() {
    getTblItems().clear();

    for (Pointer<?> pointer : (Collection<? extends Pointer>) collectionToEdit) {
      getTblItems().add(pointer);
    }

  }

  @Override
  public void updateEditors(int selectionIndex) {
    // this editor has no sub editors.
  }
}
