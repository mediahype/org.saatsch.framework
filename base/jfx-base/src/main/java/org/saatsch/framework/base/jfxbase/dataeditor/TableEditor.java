package org.saatsch.framework.base.jfxbase.dataeditor;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.FlowPane;
import org.joda.beans.Bean;
import org.joda.beans.MetaBean;
import org.joda.beans.MetaProperty;
import org.joda.beans.Property;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

import static org.saatsch.framework.base.jfxbase.dataeditor.PropertyUtil.isPropertyAnnotatedWith;
import static org.saatsch.framework.base.jfxbase.dataeditor.PropertyUtil.isHidden;

/**
 * used on List of Beans
 */
public class TableEditor extends AbstractListEditor<Object> implements Repaintable {

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
    MetaBean metaBean;
    try {
      metaBean = MetaBean.of(genericClass);
    } catch (IllegalArgumentException e) {
      LOG.error("cannot create editor for {}: metaBean not found.", property.name());
      return;
    }

    for (MetaProperty<?> m : metaBean.metaPropertyIterable()) {
      if (!isHidden(m)) {
        addColumn(m);
      }
    }
  }

  /**
   * adds a column to the table.
   *
   * @param m the {@link MetaProperty} the new column should display
   */
  protected void addColumn(MetaProperty<?> m) {

    TableColumn<Object, String> col = new TableColumn<>(m.name());
    col.setSortable(false);

    if (isPropertyAnnotatedWith(m, DataEditor.class)){
      DataEditor annotation = m.annotation(DataEditor.class);
      col.setPrefWidth(annotation.columnWidth());
    }

    col.setCellValueFactory(data -> {
      Object o = m.get((Bean) data.getValue());
      return new SimpleStringProperty(o.toString());
    });
    tblTable.getColumns().add(col);

  }

  @Override
  protected void btnRemovePressed(ActionEvent event) {
    collectionToEdit.remove(getSelection());
    saveObject();
    repaintTable();
  }

  @Override
  protected void btnAddPressed(ActionEvent event) {
    Bean newInstance = PropertyUtil.newInstance(genericClass);


    // add it to the list and save the containing Bean
    ((Collection<Bean>) property.get()).add(newInstance);
    saveObject();
    repaintTable();

    select(newInstance);
  }

  private void select(Bean toSelect) {
    for (Object item : tblTable.getItems()) {
      if (item instanceof Bean) {
        Bean bean = (Bean) item;
        if (bean.equals(toSelect)) {
          tblTable.getSelectionModel().select(item);
          return;
        }
      }
    }
  }

  @Override
  protected void repaintTable() {
    if (collectionToEdit.isEmpty()) {
      return;
    }

    tblTable.getItems().clear();

    for (Object bean : collectionToEdit) {
      tblTable.getItems().add(bean);
    }

  }

  @Override
  public void updateEditors(int selectionIndex) {
    if (selectionIndex != -1) {
      drawEditors((Bean) tblTable.getSelectionModel().getSelectedItem());

    }
  }

  private void drawEditors(Bean bean) {

    editorsPane.getChildren().clear();

    EditorCreator.createEditors(editorsPane, bean, objectToEdit);


  }


  @Override
  public void repaint() {
    repaintTable();
  }
}
