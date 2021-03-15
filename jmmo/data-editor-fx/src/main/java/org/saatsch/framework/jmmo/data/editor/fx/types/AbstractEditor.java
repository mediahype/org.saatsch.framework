package org.saatsch.framework.jmmo.data.editor.fx.types;

import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;
import org.joda.beans.Bean;
import org.joda.beans.Property;
import org.saatsch.framework.jmmo.cdi.container.JmmoContext;
import org.saatsch.framework.jmmo.data.DataSink;
import org.saatsch.framework.jmmo.data.annotations.JmmoDoc;
import org.saatsch.framework.jmmo.data.api.PropertyUtil;
import org.saatsch.framework.jmmo.data.editor.fx.Styles;
import org.saatsch.framework.jmmo.data.editor.fx.base.Repaintable;

public abstract class AbstractEditor extends VBox {

  protected final Bean objectToEdit;
  protected final Property<Object> property;

  public AbstractEditor(Property<Object> property, Bean objectToEdit){
    super();
    this.objectToEdit = objectToEdit;
    this.property = property;
    setStyle(Styles.abstractEditor);
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
    Parent p = getParent();
    while (null != p) {
      if (p instanceof Repaintable) {
        ((Repaintable) p).repaint();
      }

      p = p.getParent();
    }
  }

}
