package org.saatsch.framework.base.jfxbase.dataeditor;

import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;
import org.joda.beans.Bean;
import org.joda.beans.Property;

import java.util.Optional;

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

    if (PropertyUtil.isPropertyAnnotatedWith(property, DataEditorDoc.class)) {
      DataEditorDoc annotation = property.metaProperty().annotation(DataEditorDoc.class);
      propertyNameLabel.setTooltip(new Tooltip(annotation.value()));
    }

    setPrefWidth(USE_COMPUTED_SIZE);
    setPrefHeight(USE_COMPUTED_SIZE);

  }

  protected void saveObject() {
    //TODO: introduce callback
    repaintParents();
  }

  private void repaintParents() {

    Parent p = getParent();
    while (null != p) {
      if (p instanceof Repaintable) {
        ((Repaintable) p).repaint();
      }
      p = p.getParent();
    }
  }

  protected <Y> Optional<Y> getParent(Class<Y> clazz){
    Parent p = getParent();
    while (null != p) {
      if (p.getClass().equals(clazz)) {
        return Optional.of((Y) p);
      }
      p = p.getParent();
    }
    return Optional.empty();
  }

}
