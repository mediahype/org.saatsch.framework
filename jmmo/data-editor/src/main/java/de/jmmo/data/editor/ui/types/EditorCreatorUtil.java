package de.jmmo.data.editor.ui.types;

import static de.jmmo.data.api.PropertyUtil.firstTypeArg;
import static de.jmmo.data.api.PropertyUtil.genericClass;
import static de.jmmo.data.api.PropertyUtil.isIntlString;
import static de.jmmo.data.api.PropertyUtil.isPropertyAnnotatedWith;
import static de.jmmo.data.api.PropertyUtil.isSupportedCollection;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.joda.beans.Bean;
import org.joda.beans.Property;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.jmmo.cdi.container.JmmoContext;
import de.jmmo.data.annotations.JmmoAppId;
import de.jmmo.data.annotations.JmmoEditorHidden;
import de.jmmo.data.annotations.JmmoFloat;
import de.jmmo.data.api.Pointer;
import de.jmmo.data.api.model.JmmoImage;
import de.jmmo.data.editor.ui.types.builtin.BoolEditor;
import de.jmmo.data.editor.ui.types.builtin.EnumEditor;
import de.jmmo.data.editor.ui.types.builtin.FloatEditor;
import de.jmmo.data.editor.ui.types.builtin.ImageEditor;
import de.jmmo.data.editor.ui.types.builtin.IntEditor;
import de.jmmo.data.editor.ui.types.builtin.IntlStringEditor;
import de.jmmo.data.editor.ui.types.builtin.NestedBeanEditor;
import de.jmmo.data.editor.ui.types.builtin.PointerEditor;
import de.jmmo.data.editor.ui.types.builtin.StringEditor;
import de.jmmo.data.editor.ui.types.builtin.table.EnumListEditor;
import de.jmmo.data.editor.ui.types.builtin.table.PointerListEditor;
import de.jmmo.data.editor.ui.types.builtin.table.StringCollectionEditor;
import de.jmmo.data.editor.ui.types.builtin.table.TableEditor;
import de.jmmo.data.editor.ui.types.custom.CustomTypes;

/**
 * 
 * 
 * @author saatsch
 *
 */
public class EditorCreatorUtil {

  private static final Logger LOG = LoggerFactory.getLogger(EditorCreatorUtil.class);

  private EditorCreatorUtil() {}

  /**
   * creates editors for a Bean
   * 
   * @param composite the composite in which to create the editors.
   * @param bean the Bean for which to create the editors.
   * @param objectToEdit the top level, persistable Bean. This Bean will be persisted if any of the
   *        property editors signals that the object to edit should be saved.
   */
  public static void createEditors(Composite composite, Bean bean, Bean objectToEdit) {

    // in case of toggling the edit mode with no bean selected, the following can happen:
    if (bean == null) {
      return;
    }

    EditorLayouter layouter = new EditorLayouter(composite);


    for (String prop : bean.propertyNames()) {

      if (isPropertyAnnotatedWith(bean.property(prop), JmmoAppId.class)) {
        continue;
      }
      if (isPropertyAnnotatedWith(bean.property(prop), JmmoEditorHidden.class)) {
        continue;
      }

      Property<Object> property = bean.property(prop);
      if (isSupportedCollection(property) || property.get() instanceof Bean) {
        layouter.nextLayoutIsGrid();
      } else {
        layouter.nextLayoutIsRow();
      }
      createEditorForField(layouter.getToDrawInto(), property, objectToEdit);
    }

  }


  /**
   * creates editor(s) for a given Property. If the Property represents a Bean, recurses into that
   * Bean.
   * 
   * @param parent the composite in which to create the editors.
   * @param property the property for which to create
   * @param objectToEdit the top level, persistable Bean. This Bean will be persisted if any of the
   *        property editors signals that the object to edit should be saved.
   * @return the created editor
   */
  public static AbstractEditorComposite createEditorForField(Composite parent,
      Property<Object> property, Bean objectToEdit) {

    String propType = property.metaProperty().propertyType().toString();

    if (isSupportedCollection(property)) {
      // The collection...

      // ... is a List of Pointer<>s
      if (Pointer.class.equals(firstTypeArg(property.metaProperty()))) {
        return createPointerCollection(parent, property, objectToEdit);
      }

      // ... is a List of Enum Constants.
      Class<?> genericClass = genericClass(property.metaProperty());
      if (null != genericClass && genericClass.isEnum()) {
        return createEnumCollection(parent, property, objectToEdit);
      }

      if (null != genericClass && genericClass.equals(String.class)) {
        return createStringCollection(parent, property, objectToEdit);
      }

      // ...
      return createTable(parent, property, objectToEdit);
    }



    if (null == property.get()) {
      LOG.warn("property.get() on property:{} returned null. No editor will be created.",
          property.name());
      return null;
    }


    if (propType.contains("java.lang.Integer") || propType.equals("int")) {
      return createInteger(parent, property, objectToEdit);
    }
    if (propType.contains("java.lang.String")) {
      return createString(parent, property, objectToEdit);
    }
    if (propType.contains("java.lang.Boolean") || propType.equals("boolean") ) {
      return createBoolean(parent, property, objectToEdit);
    }
    if (propType.contains("java.lang.Float") || propType.equals("float")) {
      return createFloat(parent, property, objectToEdit);
    }

    if (property.get() instanceof Pointer) {
      return createPointer(parent, property, objectToEdit);
    }

    if (property.get().getClass().equals(JmmoImage.class)) {
      return createImage(parent, property, objectToEdit);
    }

    if (property.get().getClass().isEnum()) {
      return createEnum(parent, property, objectToEdit);
    }

    CustomTypes customTypes = JmmoContext.getBean(CustomTypes.class);
    if (customTypes.contains(property.get().getClass())) {
      return customTypes.instantiateEditorFor(property.get().getClass(), parent, property, SWT.NONE,
          objectToEdit);
    }

    if (property.get() instanceof Bean) {
      return createNestedBean(parent, property, objectToEdit);
    }

    
    
    LOG.info("No Type Editor for class:{} --- property:{}", property.get().getClass().getName(), property.toString());

    return null;
  }


  private static AbstractEditorComposite createImage(Composite parent, Property<Object> property,
      Bean objectToEdit) {
    return new ImageEditor(parent, property, objectToEdit);
  }

  private static AbstractEditorComposite createPointer(Composite parent, Property<Object> property,
      Bean objectToEdit) {
    return new PointerEditor(parent, property, objectToEdit);
  }


  private static AbstractEditorComposite createNestedBean(Composite parent,
      Property<Object> property, Bean objectToEdit) {
    return new NestedBeanEditor(parent, property, objectToEdit);
  }


  private static AbstractEditorComposite createFloat(Composite parent, Property<Object> property,
      Bean beanToEdit) {
    if (isPropertyAnnotatedWith(property, JmmoFloat.class)) {
      return new FloatEditor(parent, property, beanToEdit);
    }
    return null;
  }


  private static AbstractEditorComposite createPointerCollection(Composite parent,
      Property<Object> property, Bean beanToEdit) {
    return new PointerListEditor(parent, property, beanToEdit);
  }

  private static AbstractEditorComposite createInteger(Composite parent, Property<Object> property,
      Bean beanToEdit) {
    return new IntEditor(parent, property, beanToEdit);
  }

  private static AbstractEditorComposite createString(Composite parent, Property<Object> property,
      Bean beanToEdit) {

    if (isIntlString(property)) {
      return new IntlStringEditor(parent, property, beanToEdit);
    } else {
      return new StringEditor(parent, property, beanToEdit);
    }

  }

  private static AbstractEditorComposite createTable(Composite parent, Property<Object> property,
      Bean beanToEdit) {
    return new TableEditor(parent, property, beanToEdit);
  }

  private static AbstractEditorComposite createBoolean(Composite parent, Property<Object> property,
      Bean beanToEdit) {
    return new BoolEditor(parent, property, beanToEdit);
  }

  private static AbstractEditorComposite createEnum(Composite parent, Property<Object> property,
      Bean beanToEdit) {
    return new EnumEditor(parent, property, beanToEdit);
  }

  private static AbstractEditorComposite createEnumCollection(Composite parent,
      Property<Object> property, Bean beanToEdit) {
    return new EnumListEditor(parent, property, beanToEdit);
  }


  private static AbstractEditorComposite createStringCollection(Composite parent,
      Property<Object> property, Bean beanToEdit) {
    return new StringCollectionEditor(parent, property, beanToEdit);
  }

}
