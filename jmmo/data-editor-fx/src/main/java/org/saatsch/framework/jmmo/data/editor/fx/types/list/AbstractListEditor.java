package org.saatsch.framework.jmmo.data.editor.fx.types.list;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.joda.beans.Bean;
import org.joda.beans.Property;
import org.saatsch.framework.jmmo.data.api.PropertyUtil;
import org.saatsch.framework.jmmo.data.editor.fx.types.AbstractEditor;

public abstract class AbstractListEditor<T> extends AbstractEditor {

  /**
   * the type of the entries in the {@link #collectionToEdit}
   */
  protected Class<? extends Bean> genericClass;

  /**
   * the Collection that gets edited by this Editor.
   */
  protected Collection<T> collectionToEdit;


  /**
   * the table that displays the objects in the collection.
   */
  protected TableView<T> tblTable;

  private SmallButton btnAdd;
  private SmallButton btnRemove;
  private SmallButton btnUp;
  private SmallButton btnDown;

  public AbstractListEditor(Property<Object> property, Bean objectToEdit) {
    super(property, objectToEdit);

    if (null != property) {
      genericClass =
          (Class<? extends Bean>) PropertyUtil.firstTypeArgRecurse(property.metaProperty());
      collectionToEdit = (Collection<T>) property.get();
    }

    tblTable = new TableView<>();
    tblTable.getSelectionModel().selectedIndexProperty().addListener(n -> {
      updateEditors(getSelectedIndex());
    });

    getChildren().add(createContent());

    fillContent();

    setPrefHeight(200);

  }

  private void fillContent() {

    if (collectionToEdit instanceof List) {
      btnDown.setDisable(false);
      btnUp.setDisable(false);
    }

    // the collection could be unmodifiable...
    try {
      collectionToEdit.addAll(Collections.emptyList());
    } catch (UnsupportedOperationException e) {
      btnAdd.setDisable(true);
      btnRemove.setDisable(true);

    }


  }


  private HBox createContent() {

    HBox hBox = new HBox();
    hBox.getChildren().add(tblTable);
    HBox.setHgrow(tblTable, Priority.ALWAYS);

    VBox buttons = new VBox();
    buttons.setPrefWidth(50);

    // button add
    btnAdd = new SmallButton("+");
    btnAdd.setOnAction(this::btnAddPressed);
    buttons.getChildren().add(btnAdd);

    // button remove
    btnRemove = new SmallButton("-");
    btnRemove.setOnAction(this::btnRemovePressed);
    buttons.getChildren().add(btnRemove);

    // button UP
    btnUp = new SmallButton("u");
    btnUp.setOnAction(this::moveUp);
    btnUp.setDisable(true);
    buttons.getChildren().add(btnUp);

    // button DOWN
    btnDown = new SmallButton("d");
    btnDown.setOnAction(this::moveDown);
    btnDown.setDisable(true);
    buttons.getChildren().add(btnDown);

    hBox.getChildren().add(buttons);
    HBox.setHgrow(buttons, Priority.NEVER);

    return hBox;

  }


  private void moveDown(ActionEvent event) {
    int selectedIndex = getSelectedIndex();
    if (selectedIndex >= collectionToEdit.size() - 1 || selectedIndex == -1) {
      return;
    }

    if (collectionToEdit instanceof List) {
      Collections.swap((List<?>) collectionToEdit, selectedIndex, selectedIndex + 1);
      saveObject();
    }
    repaintTable();
    tblTable.getSelectionModel().select(selectedIndex + 1);
  }


  private void moveUp(ActionEvent event) {
    // can only move up if index is 1 or higher
    int selectedIndex = getSelectedIndex();
    if (selectedIndex < 1) {
      return;
    }

    if (collectionToEdit instanceof List) {
      Collections.swap((List<?>) collectionToEdit, selectedIndex, selectedIndex - 1);
      saveObject();
    }
    repaintTable();
    tblTable.getSelectionModel().select(selectedIndex - 1);

  }

  /**
   * @return the (zero based) index of the currently selected item in the table or -1 if no item is
   * selected
   */
  protected int getSelectedIndex() {

    if (tblTable.getSelectionModel().isEmpty()) {
      return -1;
    }

    return tblTable.getSelectionModel().getSelectedIndex();

  }

  protected T getSelection() {
    return tblTable.getSelectionModel().getSelectedItem();
  }

  protected ObservableList<T> getTblItems() {
    return tblTable.getItems();
  }

  protected abstract void btnRemovePressed(ActionEvent event);

  protected abstract void btnAddPressed(ActionEvent event);

  /**
   * tells the implementation to repaint the table {@link #tblTable}
   */
  protected abstract void repaintTable();

  /**
   * called when a list item was selected. The implementation must update it's editors (if it has
   * any).
   *
   * @param selectionIndex the zero-relative index of the item which is currently selected in the UI
   * widget, or -1 if no item is selected.
   */
  public abstract void updateEditors(int selectionIndex);

}
