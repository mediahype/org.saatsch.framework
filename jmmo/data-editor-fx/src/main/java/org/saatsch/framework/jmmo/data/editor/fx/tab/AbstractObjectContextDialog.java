package org.saatsch.framework.jmmo.data.editor.fx.tab;

import javafx.event.ActionEvent;
import javafx.scene.control.ButtonType;
import org.joda.beans.Bean;
import org.saatsch.framework.base.jfxbase.control.*;
import org.saatsch.framework.jmmo.cdi.container.JmmoContext;
import org.saatsch.framework.jmmo.data.api.AppIdSuggester;
import org.saatsch.framework.jmmo.data.api.DataConfig;

import java.util.List;

public abstract class AbstractObjectContextDialog extends Dialog<Object> {

  protected final AppIdSuggester suggester = JmmoContext.getBean(AppIdSuggester.class);
  protected final TextField txtName;
  protected final TextField txtId = new TextField();
  protected final ComboBox<String> cmbSubClasses;
  protected Class<? extends Bean> objectClass;
  protected final List<Class<?>> subclasses;

  private final Button suggest = new Button("Suggest").withAction(this::suggest);

  public AbstractObjectContextDialog(Class<? extends Bean> objectClass) {
    this.objectClass = objectClass;
    subclasses = JmmoContext.getBean(DataConfig.class).getSpecializationsFor(objectClass);
    setTitle("Add new " + objectClass.getSimpleName());

    GridPane content = new GridPane();

    new Label("Name:").withParent(content).withLayoutColRow(0,0);
    txtName = new TextField().withParent(content).withLayoutColRow(1, 0);

    new Label("ID:").withParent(content).withLayoutColRow(0,1);
    new HBox().withParent(content).withLayoutColRow(1,1).withChildren(txtId, suggest);

    new Label("Class:").withParent(content).withLayoutColRow(0, 2);
    cmbSubClasses = new ComboBox<String>().withParent(content).withLayoutColRow(1, 2);

    withButtons(ButtonType.OK);

    getDialogPane().setContent(content);

    postProcess();



  }

  private void postProcess() {
    for (Class<?> clazz : subclasses) {
      cmbSubClasses.getItems().add(clazz.getSimpleName());
    }

    // preselect first item if possible...
    if (cmbSubClasses.getItems().size() > 0) {
      cmbSubClasses.getSelectionModel().select(0);
    }
  }

  protected void suggest(ActionEvent actionEvent) {
    txtId.setText(suggester.suggest(txtName.getText(), objectClass));
  }


}
