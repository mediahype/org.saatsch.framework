package org.saatsch.framework.jmmo.data.editor.fx.tab;

import javafx.event.ActionEvent;
import javafx.scene.control.ContextMenu;
import org.joda.beans.Bean;
import org.saatsch.framework.base.jfxbase.control.MenuItem;
import org.saatsch.framework.jmmo.cdi.container.JmmoContext;
import org.saatsch.framework.jmmo.data.editor.fx.DataEditorFxApp;

public class LeftPaneContextMenu extends ContextMenu {

  private final Class<? extends Bean> objectClass;

  public LeftPaneContextMenu(Class<? extends Bean> clazz) {

    this.objectClass = clazz;

    MenuItem add = new MenuItem("Add " + clazz.getSimpleName() + "...").withAction(this::addObject);
    MenuItem delete = new MenuItem("Delete " + clazz.getSimpleName() + "...");
    MenuItem edit = new MenuItem("Edit " + clazz.getSimpleName() + "...");
    MenuItem duplicate = new MenuItem("Duplicate " + clazz.getSimpleName());

    getItems().addAll(add,delete,edit,duplicate);

  }

  private void addObject(ActionEvent actionEvent) {
    AddObjectDialog dialog = new AddObjectDialog(objectClass);
    dialog.showAndWait().ifPresent(result -> {
      JmmoContext.getBean(DataEditorFxApp.class).getActiveTab().reload();
    });
  }
}
