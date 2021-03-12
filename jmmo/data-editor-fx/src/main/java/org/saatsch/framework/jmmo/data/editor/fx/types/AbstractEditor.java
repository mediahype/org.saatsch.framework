package org.saatsch.framework.jmmo.data.editor.fx.types;

import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.joda.beans.Bean;
import org.joda.beans.Property;
import org.saatsch.framework.jmmo.cdi.container.JmmoContext;
import org.saatsch.framework.jmmo.data.DataSink;
import org.saatsch.framework.jmmo.data.annotations.JmmoDoc;
import org.saatsch.framework.jmmo.data.api.PropertyUtil;

public abstract class AbstractEditor extends VBox {

  String cssLayout = "-fx-border-color: red;\n" +
      "-fx-border-insets: 2;\n" +
      "-fx-border-width: 1;\n" +
      "-fx-border-style: dashed;\n";


  protected final Bean objectToEdit;
  protected final Property<Object> property;

  public AbstractEditor(Property<Object> property, Bean objectToEdit){
    super();
    this.objectToEdit = objectToEdit;
    this.property = property;
    setStyle(cssLayout);
    createContents();
  }

  private void createContents() {

    Label propertyNameLabel = new Label(property.name());

    getChildren().add(propertyNameLabel);

    if (PropertyUtil.isPropertyAnnotatedWith(property, JmmoDoc.class)) {
      JmmoDoc annotation = property.metaProperty().annotation(JmmoDoc.class);
      propertyNameLabel.setTooltip(new Tooltip(annotation.value()));
    }

    setPrefWidth(USE_COMPUTED_SIZE);
    setPrefHeight(USE_COMPUTED_SIZE);

  }

  protected void saveObject() {

    //TODO: wrap this in an action

    DataSink data = JmmoContext.getBean(DataSink.class);
    data.save(  objectToEdit != null ? objectToEdit : property.bean()  );


    repaintParents();



  }

  private void repaintParents() {
    //TODO
  }

}
