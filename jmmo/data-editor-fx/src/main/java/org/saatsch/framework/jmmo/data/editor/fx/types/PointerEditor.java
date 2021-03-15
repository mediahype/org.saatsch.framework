package org.saatsch.framework.jmmo.data.editor.fx.types;

import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import org.joda.beans.Bean;
import org.joda.beans.Property;
import org.saatsch.framework.jmmo.data.api.Pointer;
import org.saatsch.framework.jmmo.data.api.PropertyUtil;
import org.saatsch.framework.jmmo.data.api.model.JmmoImage;
import org.saatsch.framework.jmmo.data.editor.fx.dialog.ImagesWindow;
import org.saatsch.framework.jmmo.data.editor.fx.dialog.ReferenceTargetSelectionDialog;

public class PointerEditor extends  AbstractEditor{

  private final TextField txtContent;

  public PointerEditor(Property<Object> property,
      Bean objectToEdit) {
    super(property, objectToEdit);

    txtContent = new TextField();
    txtContent.setDisable(true);
    getChildren().add(txtContent);

    HBox box = new HBox();
    getChildren().add(box);

    Button btnSelect = new Button("Select");
    btnSelect.setOnAction(this::selectObject);
    box.getChildren().add(btnSelect);

    Button btnGo = new Button("Go");
    btnGo.setOnAction(this::go);
    box.getChildren().add(btnGo);

    Button btnReset = new Button("-");
    btnReset.setOnAction(this::resetPointer);
    box.getChildren().add(btnReset);

    fillContents();


  }


  private void fillContents() {
    Pointer<?> pointer = (Pointer<?>) property.get();
    if (null != pointer) {
      txtContent.setText(pointer.asString());
    } else {
      txtContent.setText("");
    }
  }


  private void resetPointer(ActionEvent actionEvent) {
    Pointer<?> pointer = (Pointer<?>) property.get();
    pointer.setTargetCoodinate(pointer.getBaseClass(), null);
    saveObject();
    fillContents();
  }

  private void selectObject(ActionEvent actionEvent) {
    Dialog<Object> diag = null;
    // open select image dialog, if it is an image.
    Class<?> pointerType = PropertyUtil.getPointerType(property);
    if (pointerType.equals(JmmoImage.class)) {
      diag = new ImagesWindow();
    } else {
      diag = new ReferenceTargetSelectionDialog( property);
    }


    Optional<Object> result = diag.showAndWait();
    if (result.isPresent()){
      Pointer<?> pointer = (Pointer<?>) property.get();
      pointer.setTargetCoodinate(PropertyUtil.getPointerType(property),
          (String) PropertyUtil.getAppIdProperty((Bean) result.get()).get());
      property.set(pointer);
      saveObject();
      fillContents();
    }
  }


  private void go(ActionEvent actionevent) {
    
  }


}
