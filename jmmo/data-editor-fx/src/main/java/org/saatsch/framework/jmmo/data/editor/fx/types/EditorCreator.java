package org.saatsch.framework.jmmo.data.editor.fx.types;

import static org.saatsch.framework.jmmo.data.api.PropertyUtil.*;

import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import org.joda.beans.Bean;
import org.joda.beans.Property;
import org.saatsch.framework.jmmo.cdi.container.JmmoContext;
import org.saatsch.framework.jmmo.data.annotations.JmmoAppId;
import org.saatsch.framework.jmmo.data.annotations.JmmoEditorHidden;
import org.saatsch.framework.jmmo.data.api.Pointer;
import org.saatsch.framework.jmmo.data.api.PropertyUtil;
import org.saatsch.framework.jmmo.data.api.model.JmmoImage;
import org.saatsch.framework.jmmo.data.editor.fx.types.list.PointerListEditor;
import org.saatsch.framework.jmmo.data.editor.fx.types.list.TableEditor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EditorCreator {

  private static final Logger LOG = LoggerFactory.getLogger(EditorCreator.class);


  public static void createEditors(Pane pane, Bean toEdit, Bean toSave){

    for (String prop : toEdit.propertyNames()) {

      if (isPropertyAnnotatedWith(toEdit.property(prop), JmmoAppId.class)) {
        continue;
      }
      if (isPropertyAnnotatedWith(toEdit.property(prop), JmmoEditorHidden.class)) {
        continue;
      }

      Property<Object> property = toEdit.property(prop);


      createEditorForField(pane, property, toSave);


    }

  }

  private static void createEditorForField(Pane parent,
      Property<Object> property, Bean toSave) {

    if (PropertyUtil.isSupportedCollection(property)) {
      // The collection...

      // ... is a List of Pointer<>s
      if (Pointer.class.equals(PropertyUtil.firstTypeArg(property.metaProperty()))) {
        createPointerCollection(parent, property, toSave);
        return;
      }

      // ... is a List of Enum Constants.
      Class<?> genericClass = PropertyUtil.genericClass(property.metaProperty());
      if (null != genericClass && genericClass.isEnum()) {
        createEnumCollection(parent, property, toSave);
        return;
      }

      // .. is a List of Strings
      if (null != genericClass && genericClass.equals(String.class)) {
        createStringCollection(parent, property, toSave);
        return;
      }

      // ... is any other supported Collection
      createTable(parent, property, toSave);
      return;
    }

    if (null == property.get()) {
      LOG.warn("property.get() on property:{} returned null. No editor will be created.",
          property.name());

    }

    String propType = property.metaProperty().propertyType().toString();

    if (propType.contains("java.lang.Integer") || propType.equals("int")) {
       createInteger(parent, property, toSave);
       return;
    }
    if (propType.contains("java.lang.String")) {
       createString(parent, property, toSave);
       return;
    }
    if (propType.contains("java.lang.Boolean") || propType.equals("boolean") ) {
       createBoolean(parent, property, toSave);
       return;
    }
    if (propType.contains("java.lang.Float") || propType.equals("float")) {
       createFloat(parent, property, toSave);
       return;
    }

    if (property.get() instanceof Pointer) {
       createPointer(parent, property, toSave);
       return;
    }

    if (property.get().getClass().equals(JmmoImage.class)) {
       createImage(parent, property, toSave);
       return;
    }

    if (property.get().getClass().isEnum()) {
       createEnum(parent, property, toSave);
       return;
    }

//    CustomTypes customTypes = JmmoContext.getBean(CustomTypes.class);
//    if (customTypes.contains(property.get().getClass())) {
//       customTypes.instantiateEditorFor(property.get().getClass(), parent, property, SWT.NONE,
//          toSave);
//    }

    if (property.get() instanceof Bean) {
       createNestedBean(parent, property, toSave);
       return;
    }



    LOG.info("No Type Editor for class:{} --- property:{}", property.get().getClass().getName(), property.toString());


  }

  private static void createNestedBean(Pane parent, Property<Object> property, Bean toSave) {
    parent.getChildren().add(new NestedBeanEditor(property,toSave));
  }

  private static void createEnum(Pane parent, Property<Object> property, Bean toSave) {
    parent.getChildren().add(new EnumEditor(property, toSave));
  }

  private static void createImage(Pane parent, Property<Object> property, Bean toSave) {
    parent.getChildren().add(new ImageEditor(property, toSave));
  }

  private static void createPointer(Pane parent, Property<Object> property, Bean toSave) {
    parent.getChildren().add(new PointerEditor(property,toSave));
  }

  private static void createFloat(Pane parent, Property<Object> property, Bean toSave) {
    parent.getChildren().add(new FloatEditor(property,toSave));
  }

  private static void createBoolean(Pane parent, Property<Object> property, Bean toSave) {
    parent.getChildren().add(new BoolEditor(property, toSave));
  }

  private static void createString(Pane parent, Property<Object> property, Bean toSave) {

    if (PropertyUtil.isIntlString(property)) {
      parent.getChildren().add(new IntlStringEditor(property,toSave));

    } else {
      parent.getChildren().add(new StringEditor(property,toSave));
    }


  }

  private static void createInteger(Pane parent, Property<Object> property, Bean toSave) {
    parent.getChildren().add(new IntEditor(property,toSave));
  }

  private static void createTable(Pane parent, Property<Object> property, Bean toSave) {
    parent.getChildren().add(new TableEditor(property,toSave));
  }

  private static void createStringCollection(Pane parent, Property<Object> property, Bean toSave) {
    parent.getChildren().add(new Label("|" +property.name() + ":createEnumCollection"  ));
  }

  private static void createEnumCollection(Pane parent, Property<Object> property, Bean toSave) {
    parent.getChildren().add(new Label("|" + property.name() + ":createStringCollection"  ));
  }

  private static void createPointerCollection(Pane parent, Property<Object> property, Bean toSave) {
    parent.getChildren().add(new PointerListEditor(property,toSave));
  }


}
