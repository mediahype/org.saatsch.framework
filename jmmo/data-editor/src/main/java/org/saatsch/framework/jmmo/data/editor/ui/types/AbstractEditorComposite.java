package org.saatsch.framework.jmmo.data.editor.ui.types;


import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.joda.beans.Bean;
import org.joda.beans.Property;

import org.saatsch.framework.jmmo.cdi.container.JmmoContext;
import org.saatsch.framework.jmmo.data.DataSink;
import org.saatsch.framework.jmmo.data.annotations.JmmoDoc;
import org.saatsch.framework.jmmo.data.api.PropertyUtil;

/**
 * Abstract parent class of all type Editors. Custom Type Editor implementations MUST use this as
 * parent class.
 * 
 * It exposes the following functionality to it's children:
 * <ul>
 * <li>the variable {@link #property}</li>
 * <li>the method {@link #saveObject()}</li>
 * </ul>
 * 
 * Implementations need not be thread safe. A new instance of an implementation is created everytime
 * an editor is displayed. Implementations must implement the constructor
 * {@link AbstractEditorComposite#AbstractEditorComposite(Composite, Property, int, Bean)} and call
 * the super constructor. <br>
 * Do not execute time consuming logic or data reads inside the constructor. When you need such
 * things consider creating an {@link ApplicationScoped} class and access it via the
 * {@link JmmoContext} utility. Implementations also cannot use {@link Inject}ion but need to fall
 * back to {@link JmmoContext}.
 * 
 * Look for example implementations in the <code>de.jmmo.data.editor.ui.types.builtin</code>
 * package.
 * 
 * 
 * @author saatsch
 * 
 */
public abstract class AbstractEditorComposite extends DebugComposite {
  
  /**
   * the property that gets edited
   */
  protected Property<Object> property;

  /**
   * This is the object that is saved, when {@link #saveObject()} is called. It (directly or
   * indirectly) contains the {@link #property}.
   */
  protected Bean objectToEdit;

  /**
   * @param parent this is passed on to the parent constructor:
   *        {@link Composite#Composite(Composite, int)}
   * @param property the property to edit
   * @param style this is passed on to the parent constructor:
   *        {@link Composite#Composite(Composite, int)}
   * @param objectToEdit the Object that gets edited.
   */
  public AbstractEditorComposite(Composite parent, Property<Object> property, int style,
      Bean objectToEdit) {
    super(parent, style);
    this.objectToEdit = objectToEdit;
    this.property = property;
    createContents();
  }

  /**
   * do not manually call this constructor !
   * 
   * @wbp.parser.constructor
   */
  public AbstractEditorComposite(Composite parent, Property<Object> property, int style) {
    super(parent, style);
    this.property = property;
    createContents();
  }

  private void createContents() {
    GridLayout layout = new GridLayout(1, false);
    layout.marginWidth = 1;
    layout.marginHeight = 1;
    layout.verticalSpacing = 1;
    layout.marginTop = 1;
    setLayout(layout);
    Label lblFieldId = new Label(this, SWT.NONE);
    lblFieldId.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
    String defId = "null#null#null#null#null";
    if (property != null) {
      defId = property.name();
    }
    lblFieldId.setText(defId);

    if (PropertyUtil.isPropertyAnnotatedWith(property, JmmoDoc.class)) {
      JmmoDoc annotation = property.metaProperty().annotation(JmmoDoc.class);
      lblFieldId.setToolTipText(annotation.value());
    }
    lblFieldId.pack();
  }



  @Override
  protected void checkSubclass() {
    // Disable the check that prevents subclassing of SWT components
  }

  /**
   * save the object that contains the property that is currently edited. Implementations may want
   * to call this when the widget looses focus or at a similar event when the object should be
   * persisted.
   */
  protected void saveObject() {

    // TODO: wrap this save in an action
    DataSink data = JmmoContext.getBean(DataSink.class);

    Object toSave;
    if (null != objectToEdit) {
      toSave = objectToEdit;
    } else {
      toSave = property.bean();
    }

    data.save(toSave);

    repaintParents();

  }

  private void repaintParents() {

    Composite p = getParent();
    while (null != p) {
      if (p instanceof Repaintable) {
        ((Repaintable) p).repaint();
      }

      p = p.getParent();
    }

  }


}
