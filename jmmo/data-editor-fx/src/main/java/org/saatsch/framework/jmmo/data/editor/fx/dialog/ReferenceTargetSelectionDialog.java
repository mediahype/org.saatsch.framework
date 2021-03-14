package org.saatsch.framework.jmmo.data.editor.fx.dialog;

import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.joda.beans.Property;

public class ReferenceTargetSelectionDialog extends AbstractDialog<Object> {

  public ReferenceTargetSelectionDialog(Property<Object> property) {
    super();

    setTitle(property.name());

    VBox content = new VBox();
    getDialogPane().getChildren().add(content);

    Label lblSelection = new Label("Selection:");
    TextField txtSelection = new TextField();
    txtSelection.setDisable(true);

    HBox boxSelection = new HBox(lblSelection, txtSelection);

    content.getChildren().add(boxSelection);

  }
}
