package org.saatsch.framework.jmmo.data.editor.fx.types;

import com.sun.javafx.scene.control.skin.Utils;
import org.joda.beans.Bean;
import org.joda.beans.Property;
import org.saatsch.framework.base.jfxbase.control.Button;
import org.saatsch.framework.base.jfxbase.control.HBox;
import org.saatsch.framework.base.jfxbase.control.TextField;
import org.saatsch.framework.jmmo.cdi.container.JmmoContext;
import org.saatsch.framework.jmmo.data.api.IntlStringService;

/**
 * editor that gets constructed for editing an Intl String property.
 *
 * TODO: save
 *
 * @author saatsch
 *
 */
public class IntlStringEditor extends AbstractEditor{

  private IntlStringService stringService = JmmoContext.getBean(IntlStringService.class);

  private TextField txtContent = new TextField();

  private TextField txtReference = new TextField();

  public IntlStringEditor(Property<Object> property, Bean objectToEdit) {
    super(property, objectToEdit);

    txtReference.setDisable(true);

    HBox hBoxReference = new HBox().withChildren(txtReference, new Button("..."));

    getChildren().add(hBoxReference);
    getChildren().add(txtContent);

    fillContents();
    
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
