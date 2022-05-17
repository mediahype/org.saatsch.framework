package org.saatsch.framework.jmmo.data.editor.fx.types.list;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.FlowPane;
import org.joda.beans.Bean;
import org.joda.beans.Property;
import org.saatsch.framework.base.jfxbase.dataeditor.Repaintable;
import org.saatsch.framework.jmmo.data.editor.fx.types.StringInListEditor;
import org.saatsch.framework.jmmo.data.editor.fx.types.StringInSetEditor;

import java.util.List;
import java.util.Set;

public class StringCollectionEditor extends AbstractListEditor<String> implements Repaintable {

  FlowPane editorsPane = new FlowPane();

  public StringCollectionEditor(Property property, Bean objectToEdit) {
    super(property, objectToEdit);

    TableColumn<String, String> col = new TableColumn<>();
    col.setSortable(false);
    col.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()));
    tblTable.getColumns().add(col);

    repaintTable();

    getChildren().add(editorsPane);
  }

  @Override
  protected void btnRemovePressed(ActionEvent event) {
    if (getSelection() != null){
      collectionToEdit.remove(getSelection());
      saveObject();
      repaintTable();
    }
  }

  @Override
  protected void btnAddPressed(ActionEvent event) {
    collectionToEdit.add("");
    saveObject();
    repaintTable();
  }

  @Override
  protected void repaintTable() {
    tblTable.getItems().clear();
    collectionToEdit.forEach(s -> tblTable.getItems().add(s));
  }

  @Override
  public void updateEditors(int selectionIndex) {
    if (selectionIndex != -1) {

      editorsPane.getChildren().clear();

      if (collectionToEdit instanceof List){
        StringInListEditor editor = new StringInListEditor((List<String>) collectionToEdit, selectionIndex, this);
        editorsPane.getChildren().add(editor);
      }

      if (collectionToEdit instanceof Set){
        StringInSetEditor editor = new StringInSetEditor((Set<String>) collectionToEdit, this, tblTable);
        editorsPane.getChildren().add(editor);
      }


    }
  }


  @Override
  public void repaint() {
    saveObject();
    repaintTable();
  }
}
