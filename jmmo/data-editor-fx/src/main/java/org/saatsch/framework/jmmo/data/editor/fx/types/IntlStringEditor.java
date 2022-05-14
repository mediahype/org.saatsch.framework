package org.saatsch.framework.jmmo.data.editor.fx.types;

import com.sun.javafx.scene.control.skin.Utils;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import org.joda.beans.Bean;
import org.joda.beans.Property;
import org.saatsch.framework.base.jfxbase.control.Button;
import org.saatsch.framework.base.jfxbase.control.HBox;
import org.saatsch.framework.base.jfxbase.control.TextField;
import org.saatsch.framework.base.jfxbase.dataeditor.PropertyUtil;
import org.saatsch.framework.jmmo.cdi.container.JmmoContext;
import org.saatsch.framework.jmmo.data.annotations.JmmoGivingName;
import org.saatsch.framework.jmmo.data.api.IntlStringService;
import org.saatsch.framework.jmmo.data.editor.fx.dialog.texts.TextSelectionDialog;
import org.saatsch.framework.jmmo.data.editor.fx.eventing.Eventing;
import org.saatsch.framework.jmmo.data.editor.fx.eventing.NameChanged;

/**
 * editor that gets constructed for editing an Intl String property.
 *
 * @author saatsch
 *
 */
public class IntlStringEditor extends AbstractEditor{

  private final IntlStringService stringService = JmmoContext.getBean(IntlStringService.class);

  private final TextField txtContent = new TextField();

  private final TextField txtReference = new TextField();

  public IntlStringEditor(Property<Object> property, Bean objectToEdit) {
    super(property, objectToEdit);

    txtReference.setDisable(true);

    HBox hBoxReference = new HBox().withChildren(txtReference, new Button("...").withAction(this::selectText));

    getChildren().add(hBoxReference);
    getChildren().add(txtContent);

    txtContent.focusedProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue.equals(Boolean.FALSE)){
        save();
      }
    });

    fillContents();


    
  }

  private void selectText(ActionEvent actionEvent) {
    new TextSelectionDialog().showAndWait().ifPresent(result -> {
      property.set(result.getCoordinate());
      fillContents();
      save();

    });
  }

  private void save(){
    if (PropertyUtil.isPropertyAnnotatedWith(property, JmmoGivingName.class)){
      JmmoContext.getBean(Eventing.class).post(new NameChanged(property, objectToEdit));
    }
    saveObject();
  }

  private void fillContents() {
    txtReference.setText((String) property.get());
    setWidth(txtReference);
    txtContent.setText(stringService.loadLocalizedText((String) property.get()));
    setWidth(txtContent);


  }

  private void setWidth(TextField f){
    f.setPrefWidth(Utils.computeTextWidth(f.getFont(), f.getText(), 0.0D ) +10);
  }

}
