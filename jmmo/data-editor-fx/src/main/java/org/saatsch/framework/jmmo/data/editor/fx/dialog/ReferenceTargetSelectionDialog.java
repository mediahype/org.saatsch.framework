package org.saatsch.framework.jmmo.data.editor.fx.dialog;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import org.joda.beans.Bean;
import org.joda.beans.Property;
import org.saatsch.framework.base.jfxbase.control.Button;
import org.saatsch.framework.base.jfxbase.control.HBox;
import org.saatsch.framework.base.jfxbase.control.TextField;
import org.saatsch.framework.jmmo.data.api.PropertyUtil;
import org.saatsch.framework.jmmo.data.editor.fx.composite.FilterableBeanTree;

public class ReferenceTargetSelectionDialog extends AbstractDialog<Object> {

  private final FilterableBeanTree cmpTree;

  public ReferenceTargetSelectionDialog(Property<Object> property) {
    super(property.name());

    TextField txtSelection = new TextField();
    txtSelection.setDisable(true);


    withChildren(new HBox().withChildren(new Label("Selection:"), txtSelection));


    if (PropertyUtil.isPointer(property)) {
      cmpTree = new FilterableBeanTree((Class<? extends Bean>) PropertyUtil.getPointerType(property));
    } else {
      // must be a Collection of Pointers.
      // TODO: check if there are more possible use cases.
      cmpTree = new FilterableBeanTree(
          (Class<? extends Bean>) PropertyUtil.firstTypeArgRecurse(property.metaProperty()));

    }

    setResultConverter(param -> {
      if (param == ButtonType.OK){
        return cmpTree.getSelection();
      }
      return null;
    });

    cmpTree.getBeanTree().setOnMouseClicked(event -> {
      if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
        setResult(cmpTree.getSelection());
        close();
      }
    });

    withChildren(cmpTree);

    withChildren(new HBox().withChildren(new Button("OK").withAction(event -> setResult(cmpTree.getSelection()))));

    withButtons(ButtonType.OK);


  }

//  private void ok() {
//    setResult(tree.getSelection());
//  }

}
