package org.saatsch.framework.jmmo.data.editor.ui.composite.listproperties;

import static org.saatsch.framework.jmmo.data.api.PropertyUtil.isSupportedCollection;
import static org.saatsch.framework.jmmo.data.api.PropertyUtil.visiblePropertiesOf;

import java.util.Collection;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.joda.beans.Bean;
import org.joda.beans.Property;

/**
 * provides the properties of a {@link Bean}. The input associated with the TreeViewer this class
 * provides the content for must be a Bean.
 * 
 * @author saatsch
 *
 */
public class PropertiesProvider implements ITreeContentProvider {

  @Override
  public Object[] getElements(Object inputElement) {

    Bean b = (Bean) inputElement;
    return visiblePropertiesOf(b).toArray();

  }


  @Override
  public Object[] getChildren(Object element) {

    if (element instanceof Property) {
      Property property = (Property) element;
      Object propertyValue = property.get();
      
      if (propertyValue instanceof Bean) {
        return visiblePropertiesOf((Bean) propertyValue).toArray();
      }
      if (isSupportedCollection(property)) {
      return ((Collection) propertyValue).toArray();  
      }
    }


    return new Object[0];
  }

  @Override
  public Object getParent(Object element) {
    return null;
  }

  @SuppressWarnings("rawtypes")
  @Override
  public boolean hasChildren(Object element) {

    if (element instanceof Property) {
      Object propertyValue = ((Property) element).get();
      if (propertyValue instanceof Bean) {
        return true;
      }
    }
    return false;
  }



}
