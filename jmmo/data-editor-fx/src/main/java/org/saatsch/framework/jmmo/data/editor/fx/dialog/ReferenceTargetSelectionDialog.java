package org.saatsch.framework.jmmo.data.editor.fx.dialog;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.joda.beans.Bean;
import org.joda.beans.Property;
import org.saatsch.framework.jmmo.data.api.PropertyUtil;
import org.saatsch.framework.jmmo.data.editor.fx.composite.FilterableBeanTree;

public class ReferenceTargetSelectionDialog extends AbstractDialog<Object> {

  private final FilterableBeanTree tree;

  public ReferenceTargetSelectionDialog(Property<Object> property) {
    super();

    setTitle(property.name());
    VBox content = new VBox();
    getDialogPane().setContent(content);


    Label lblSelection = new Label("Selection:");
    TextField txtSelection = new TextField();
    txtSelection.setDisable(true);

    HBox boxSelection = new HBox(lblSelection, txtSelection);
    HBox.setHgrow(boxSelection, Priority.ALWAYS);

    content.getChildren().add(boxSelection);

    tree = new FilterableBeanTree(
        (Class<? extends Bean>) PropertyUtil.getPointerType(property));

    content.getChildren().add(tree);


    HBox buttons = new HBox();
    Button cmdOk = new Button("OK");
    cmdOk.setOnAction(event -> ok());

    buttons.getChildren().add(cmdOk);

    content.getChildren().add(buttons);

  }

  private void ok() {
    setResult(tree.getSelection());
  }

}
