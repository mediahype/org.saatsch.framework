package org.saatsch.framework.jmmo.data.editor.ui.types.builtin;


import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.PojoProperties;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.joda.beans.Bean;
import org.joda.beans.Property;

import org.saatsch.framework.jmmo.data.api.PropertyUtil;

/**
 * Editor UI for a non-internationalizable String. Respects the Jmmo String styles LONG and SHORT.
 * 
 * @author saatsch
 * 
 */
public class StringEditor extends AbstractStringEditor {
  private DataBindingContext m_bindingContext;
  

  private Text txtContent;

  protected StringPropertyWrapper stringProperty;



  /**
   * Create the composite.
   * 
   * @param parent the parent Composite
   * @param stringProperty the Property that gets edited
   * @param objectToEdit the persistable Object that contains the property
   */
  public StringEditor(Composite parent, Property<Object> stringProperty, Bean objectToEdit) {
    super(parent, stringProperty, SWT.NONE, objectToEdit);
    this.stringProperty = new StringPropertyWrapper(stringProperty);

    // txtContent = new Text(this, SWT.BORDER);
    GridData gdTxtContent = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
    gdTxtContent.widthHint = 255;
    gdTxtContent.heightHint = 100;

    if (PropertyUtil.isLongString(stringProperty)) {
      // txtContent.dispose();
      txtContent = new Text(this, SWT.BORDER | SWT.MULTI | SWT.WRAP);
      txtContent.setBounds(10, 20, 230, 19);

      txtContent.setLayoutData(gdTxtContent);
      
    } else {
      // txtContent.dispose();
      txtContent = new Text(this, SWT.BORDER);
    }

    // if not writable
    if (!stringProperty.metaProperty().style().isWritable()) {
      txtContent.setEditable(false);
    }
    m_bindingContext = initDataBindings();



  }


  private final class StringPropertyWrapper {

    private Property<Object> value;

    public StringPropertyWrapper(Property<Object> toWrap) {
      this.value = toWrap;
    }

    /**
     * this is being called via reflection by the bindings framework.
     * 
     * @return
     */
    @SuppressWarnings("unused")
    public String getValue() {
      return (String) value.get();
    }

    /**
     * this is being called via reflection by the bindings framework
     * 
     * @param value
     */
    @SuppressWarnings("unused")
    public void setValue(String value) {
      if (this.value.metaProperty().style().isWritable()) {
        this.value.set(value);
        saveObject();
      }
    }


  }

  

  protected DataBindingContext initDataBindings() {
    DataBindingContext bindingContext = new DataBindingContext();
    //
    IObservableValue observeTextTxtContentObserveWidget = WidgetProperties.text(SWT.Modify).observeDelayed(300, txtContent);
    IObservableValue valueStringPropertyObserveValue = PojoProperties.value("value").observe(stringProperty);
    bindingContext.bindValue(observeTextTxtContentObserveWidget, valueStringPropertyObserveValue, null, null);
    //
    return bindingContext;
  }
}
