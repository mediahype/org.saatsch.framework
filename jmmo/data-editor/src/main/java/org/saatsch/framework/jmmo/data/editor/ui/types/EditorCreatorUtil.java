package org.saatsch.framework.jmmo.data.editor.ui.types;

import static org.saatsch.framework.jmmo.data.api.PropertyUtil.isPropertyAnnotatedWith;
import static org.saatsch.framework.jmmo.data.api.PropertyUtil.isSupportedCollection;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.joda.beans.Bean;
import org.joda.beans.Property;
import org.saatsch.framework.jmmo.data.annotations.JmmoAppId;
import org.saatsch.framework.jmmo.data.annotations.JmmoEditorHidden;
import org.saatsch.framework.jmmo.data.annotations.JmmoFloat;
import org.saatsch.framework.jmmo.data.api.Pointer;
import org.saatsch.framework.jmmo.data.api.PropertyUtil;
import org.saatsch.framework.jmmo.data.api.model.JmmoFile;
import org.saatsch.framework.jmmo.data.editor.ui.types.builtin.BoolEditor;
import org.saatsch.framework.jmmo.data.editor.ui.types.builtin.EnumEditor;
import org.saatsch.framework.jmmo.data.editor.ui.types.builtin.FloatEditor;
import org.saatsch.framework.jmmo.data.editor.ui.types.builtin.ImageEditor;
import org.saatsch.framework.jmmo.data.editor.ui.types.builtin.IntEditor;
import org.saatsch.framework.jmmo.data.editor.ui.types.builtin.NestedBeanEditor;
import org.saatsch.framework.jmmo.data.editor.ui.types.builtin.PointerEditor;
import org.saatsch.framework.jmmo.data.editor.ui.types.builtin.table.EnumListEditor;
import org.saatsch.framework.jmmo.data.editor.ui.types.builtin.table.PointerListEditor;
import org.saatsch.framework.jmmo.data.editor.ui.types.builtin.table.StringCollectionEditor;
import org.saatsch.framework.jmmo.data.editor.ui.types.builtin.table.TableEditor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.saatsch.framework.jmmo.cdi.container.JmmoContext;
import org.saatsch.framework.jmmo.data.editor.ui.types.builtin.IntlStringEditor;
import org.saatsch.framework.jmmo.data.editor.ui.types.builtin.StringEditor;
import org.saatsch.framework.jmmo.data.editor.ui.types.custom.CustomTypes;

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
   * creates editors for a Bean toEdit
   * 
   * @param container the composite in which to create the editors.
   * @param toEdit the Bean for which to create the editors.
   * @param toSave the top level, persistable Bean. This Bean will be persisted if any of the
   *        property editors signals that the object to edit should be saved.
   */
  public static void createEditors(Composite container, Bean toEdit, Bean toSave) {

    // in case of toggling the edit mode with no bean selected, the following can happen:
    if (toEdit == null) {
      return;
    }

    EditorLayouter layouter = new EditorLayouter(container);


    for (String prop : toEdit.propertyNames()) {

      if (PropertyUtil.isPropertyAnnotatedWith(toEdit.property(prop), JmmoAppId.class)) {
        continue;
      }
      if (PropertyUtil.isPropertyAnnotatedWith(toEdit.property(prop), JmmoEditorHidden.class)) {
        continue;
      }

      Property<Object> property = toEdit.property(prop);
      if (PropertyUtil.isSupportedCollection(property) || property.get() instanceof Bean) {
        layouter.nextLayoutIsGrid();
      } else {
        layouter.nextLayoutIsRow();
      }
      createEditorForField(layouter.getToDrawInto(), property, toSave);
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

    

    if (PropertyUtil.isSupportedCollection(property)) {
      // The collection...

      // ... is a List of Pointer<>s
      if (Pointer.class.equals(PropertyUtil.firstTypeArg(property.metaProperty()))) {
        return createPointerCollection(parent, property, objectToEdit);
      }

      // ... is a List of Enum Constants.
      Class<?> genericClass = PropertyUtil.genericClass(property.metaProperty());
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

    String propType = property.metaProperty().propertyType().toString();

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

    if (property.get().getClass().equals(JmmoFile.class)) {
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
    if (PropertyUtil.isPropertyAnnotatedWith(property, JmmoFloat.class)) {
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

    if (PropertyUtil.isIntlString(property)) {
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
