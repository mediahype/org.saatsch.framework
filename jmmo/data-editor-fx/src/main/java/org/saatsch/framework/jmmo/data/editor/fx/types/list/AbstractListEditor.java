package org.saatsch.framework.jmmo.data.editor.fx.types.list;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import jiconfont.icons.font_awesome.FontAwesome;
import jiconfont.javafx.IconFontFX;
import org.joda.beans.Bean;
import org.joda.beans.Property;
import org.saatsch.framework.base.jfxbase.control.Button;
import org.saatsch.framework.base.jfxbase.control.HBox;
import org.saatsch.framework.base.jfxbase.control.VBox;
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

  private Button btnAdd;
  private Button btnRemove;
  private Button btnUp;
  private Button btnDown;

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

    if (collectionToEdit instanceof Set) {
      btnDown.setDisable(true);
      btnUp.setDisable(true);
    }

    // the collection could be unmodifiable...
    try {
      collectionToEdit.addAll(Collections.emptyList());
    } catch (UnsupportedOperationException e) {
      btnAdd.setDisable(true);
      btnRemove.setDisable(true);
      btnDown.setDisable(true);
      btnUp.setDisable(true);
    }


  }


  private HBox createContent() {



    // button add
    btnAdd = new SmallButton("add").withGraphic(new ImageView(
        IconFontFX.buildImage(FontAwesome.PLUS, 15,  Color.GREEN))).withAction(this::btnAddPressed);

    // button remove
    btnRemove = new SmallButton("remove").withGraphic(new ImageView(
        IconFontFX.buildImage(FontAwesome.MINUS, 15,  Color.RED))).withAction(this::btnRemovePressed);

    // button UP
    btnUp = new SmallButton("up").withGraphic(new ImageView(
        IconFontFX.buildImage(FontAwesome.ARROW_UP, 15,  Color.BLACK))).withAction(this::moveUp);

    // button DOWN
    btnDown = new SmallButton("down").withGraphic(new ImageView(
        IconFontFX.buildImage(FontAwesome.ARROW_DOWN, 15,  Color.BLACK))).withAction(this::moveDown);

    // container for buttons
    VBox buttons = new VBox().withChildren(btnAdd,btnRemove, btnUp, btnDown);
    buttons.setPrefWidth(50);

    // container for table and buttons
    HBox hBox = new HBox().withChildren(tblTable, buttons);
    HBox.setHgrow(tblTable, Priority.ALWAYS);
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
