package org.saatsch.framework.jmmo.data.editor.fx.types.list;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.FlowPane;
import org.joda.beans.Bean;
import org.joda.beans.MetaBean;
import org.joda.beans.MetaProperty;
import org.joda.beans.Property;
import org.saatsch.framework.jmmo.cdi.container.JmmoContext;
import org.saatsch.framework.jmmo.data.DataSink;
import org.saatsch.framework.jmmo.data.annotations.JmmoEditorHidden;
import org.saatsch.framework.jmmo.data.api.Pointer;
import org.saatsch.framework.jmmo.data.api.PropertyUtil;
import org.saatsch.framework.jmmo.data.editor.fx.base.Repaintable;
import org.saatsch.framework.jmmo.data.editor.fx.types.EditorCreator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.saatsch.framework.jmmo.data.api.PropertyUtil.isPropertyAnnotatedWith;

public class TableEditor extends AbstractListEditor<Object> implements Repaintable {

  private static final Logger LOG = LoggerFactory.getLogger(TableEditor.class);

  FlowPane editorsPane = new FlowPane();

  public TableEditor(Property<Object> property, Bean objectToEdit) {
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
      LOG.error("cannot create editor for {}", property.name());
      return;
    }

    for (MetaProperty<?> m : metaBean.metaPropertyIterable()) {

      if (!isPropertyAnnotatedWith(m, JmmoEditorHidden.class)) {
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
    col.setCellValueFactory(data -> {

      Object o = m.get((Bean) data.getValue());
      if (o instanceof Pointer) {
        return new SimpleStringProperty(((Pointer<?>) o).asString());
      }

      return new SimpleStringProperty(o.toString());
    });
    tblTable.getColumns().add(col);

  }

  @Override
  protected void btnRemovePressed(ActionEvent event) {
    collectionToEdit.remove(getSelection());
    JmmoContext.getBean(DataSink.class).save(property.bean());
    repaintTable();
  }

  @Override
  protected void btnAddPressed(ActionEvent event) {
    Bean newInstance = PropertyUtil.newInstance(genericClass);
    PropertyUtil.initPointers(newInstance);

    // add it to the list and save the containing Bean
    collectionToEdit.add(newInstance);
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
    if (collectionToEdit.isEmpty()) return;

    tblTable.getItems().clear();
    collectionToEdit.forEach(bean -> tblTable.getItems().add(bean));

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
