package de.jmmo.data.editor.ui.composite.listproperties;

import org.joda.beans.Property;

public class NameProvider extends AbstractPropertyLabelProvider {

  @Override
  public String getText(Object element) {

    if (element instanceof Property){
      Property<?> p = (Property<?>) element;
      return p.name();      
    }
    
    return null;

  }


}
