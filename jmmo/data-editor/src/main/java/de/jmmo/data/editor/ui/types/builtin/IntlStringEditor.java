package de.jmmo.data.editor.ui.types.builtin;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.PojoProperties;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.joda.beans.Bean;
import org.joda.beans.Property;

import de.jmmo.cdi.container.JmmoContext;
import de.jmmo.data.api.IntlStringService;
import de.jmmo.data.api.PropertyUtil;
import de.jmmo.data.editor.ui.types.AbstractEditorComposite;

/**
 * editor that gets constructed for editing an Intl String property.
 * 
 * @author saatsch
 *
 */
public class IntlStringEditor extends AbstractEditorComposite {
  private DataBindingContext m_bindingContext;

  private IntlStringService stringService = JmmoContext.getBean(IntlStringService.class);

  private WrappedStringProperty wrappedStringProperty;

  private final Text txtContent;

  private final Text txtReference;

  /**
   * Create the composite.
   * 
   * @param parent the parent Composite
   * @param stringProperty the Property that gets edited. Must be an Intl String
   * @param objectToEdit the persistable Object that contains the property
   */
  public IntlStringEditor(Composite parent, Property<Object> stringProperty, Bean objectToEdit) {
    super(parent, stringProperty, SWT.NONE, objectToEdit);

    this.wrappedStringProperty = new WrappedStringProperty(stringProperty);
    // create the reference textfield

    txtReference = new Text(this, SWT.BORDER);
    GridData gdTxtReference = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
    gdTxtReference.widthHint = 236;
    txtReference.setLayoutData(gdTxtReference);


    txtReference.addFocusListener(new FocusAdapter() {
      @Override
      public void focusLost(FocusEvent e) {
        property.set(txtReference.getText());
        txtContent.setText(stringService.loadLocalizedText(txtReference.getText()));
        saveObject();
      }

    });


    GridData gdTxtContent = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
    gdTxtContent.widthHint = 236;

    if (PropertyUtil.isLongString(property)) {
      txtContent = new Text(this, SWT.BORDER | SWT.MULTI);
      txtContent.setBounds(10, 20, 230, 19);
      gdTxtContent.heightHint = 85;
    } else {
      txtContent = new Text(this, SWT.BORDER);
    }

    txtContent.setLayoutData(gdTxtContent);

    // if writable
    if (!property.metaProperty().style().isWritable()) {
      txtContent.setEditable(false);
    } 

    txtContent.setBounds(10, 20, 230, 19);

    fillContents();
    m_bindingContext = initDataBindings();

    

  }

  private void fillContents() {
    txtReference.setText((String) property.get());
    txtContent.setText(stringService.loadLocalizedText((String) property.get()));
  }

  private final class WrappedStringProperty {

    private Property<Object> value;

    public WrappedStringProperty(Property<Object> toWrap) {
      this.value = toWrap;
    }

    /**
     * this is being called via reflection by the bindings framework.
     * 
     * @return
     */
    @SuppressWarnings("unused")
    public String getValue() {
      return stringService.loadLocalizedText((String) value.get());
    }

    /**
     * this is being called via reflection by the bindings framework
     * 
     * @param value
     */
    @SuppressWarnings("unused")
    public void setValue(String value) {
      if (this.value.metaProperty().style().isWritable()) {
        stringService.saveLocalizedText(txtReference.getText(), value);
      }
    }



  }


  protected DataBindingContext initDataBindings() {
    DataBindingContext bindingContext = new DataBindingContext();
    //
    IObservableValue observeTextTxtReferenceObserveWidget = WidgetProperties.text(SWT.Modify).observeDelayed(300, txtContent);
    IObservableValue valueStringPropertyObserveValue = PojoProperties.value("value").observe(wrappedStringProperty);
    bindingContext.bindValue(observeTextTxtReferenceObserveWidget, valueStringPropertyObserveValue, null, null);
    //
    return bindingContext;
  }
}
