package de.jmmo.data.editor.ui.types.builtin;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.joda.beans.Bean;
import org.joda.beans.Property;

import de.jmmo.data.annotations.JmmoInt;
import de.jmmo.data.api.PropertyUtil;
import de.jmmo.data.editor.ui.types.AbstractEditorComposite;

public class IntEditor extends AbstractEditorComposite {
  private final Spinner spinner;
  private final Label lblRange;

  public IntEditor(Composite parent, Property<Object> fieldDefinition, Bean objectToEdit) {
    super(parent, fieldDefinition, SWT.NONE, objectToEdit);


    spinner = new Spinner(this, SWT.BORDER);
    spinner.setBounds(10, 20, 73, 21);
    spinner.setMaximum(Integer.MAX_VALUE);
    spinner.setSelection((Integer) property.get());


    lblRange = new Label(this, SWT.NONE);
    lblRange.setBounds(10, 46, 129, 13);

    spinner.addFocusListener(new FocusAdapter() {

      @Override
      public void focusLost(FocusEvent e) {

        property.set(spinner.getSelection());
        saveObject();


      }

      @Override
      public void focusGained(FocusEvent e) {
        spinner.setSelection(spinner.getSelection());

      }

    });

    fillContents();


  }



  private void fillContents() {


    if (PropertyUtil.isPropertyAnnotatedWith(property, JmmoInt.class)) {
      JmmoInt meta = property.metaProperty().annotation(JmmoInt.class);
      lblRange.setText(makeRangeString(meta));
      spinner.setMaximum(meta.upperBound());
      spinner.setMinimum(meta.lowerBound());
    } else {
      spinner.setMaximum(Integer.MAX_VALUE);
    }

    // spinner.pack();
    lblRange.pack();



  }

  private static String makeRangeString(JmmoInt meta) {

    String ret = String.valueOf(meta.lowerBound()) + " ... " + String.valueOf(meta.upperBound());
    return ret;
  }


}
