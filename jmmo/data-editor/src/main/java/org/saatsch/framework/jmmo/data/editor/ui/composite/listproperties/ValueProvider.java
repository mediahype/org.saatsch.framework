package org.saatsch.framework.jmmo.data.editor.ui.composite.listproperties;

import static org.saatsch.framework.jmmo.data.api.PropertyUtil.isCollectionOfPointers;
import static org.saatsch.framework.jmmo.data.api.PropertyUtil.isIntlString;
import static org.saatsch.framework.jmmo.data.api.PropertyUtil.isSupportedCollection;
import static org.saatsch.framework.jmmo.data.api.PropertyUtil.visiblePropertiesOf;

import org.joda.beans.Bean;
import org.joda.beans.Property;

import org.saatsch.framework.jmmo.cdi.container.JmmoContext;
import org.saatsch.framework.jmmo.data.api.CustomEditorText;
import org.saatsch.framework.jmmo.data.api.IntlStringService;

public class ValueProvider extends AbstractPropertyLabelProvider {

  @Override
  public String getText(Object element) {
    
      Property<Object> p = (Property<Object>) element;
      
      if (p.get() instanceof Bean) {
        return buildForBean(p);
      } else if (isSupportedCollection(p)) {
        return buildForCollection(p);
      } else if (isIntlString(p)) {
        return JmmoContext.getBean(IntlStringService.class).loadLocalizedText((String) p.get());
      } else {
        return p.get().toString();
      }      

  }

  @SuppressWarnings("unchecked")
  private String buildForCollection(Property<Object> p) {

    if (isCollectionOfPointers(p)) {
      return p.get().toString(); 
    }

    // // TODO: go on
    // if (isCollectionOfBeans(p)) {
    //
    // Collection<Bean> beans = (Collection<Bean>) p.get();
    // StringBuilder sb = new StringBuilder();
    // for (Bean bean : beans) {
    // }
    //
    // }

    return p.get().toString();
  }

  /**
   * builds a custom String representation for a property that is a Bean.
   * 
   * @param p
   * @return
   */
  private String buildForBean(Property<Object> p) {
    StringBuilder buffer = new StringBuilder();
    buildPropertyRepresentation(buffer, p);
    return buffer.toString();
  }

  private void buildPropertyRepresentation(StringBuilder buffer, Property<Object> property) {
    buffer.append("(");
    Object object = property.get();
    if (object instanceof Bean) {
      handleBeanProperty(buffer, (Bean) object);
    } else if (isIntlString(property)) {
      buffer.append(
          JmmoContext.getBean(IntlStringService.class).loadLocalizedText((String) property.get()));
    } else {
      buffer.append(object);
    }
    buffer.append(")");
  }

  private void handleBeanProperty(StringBuilder buffer, Bean object) {
    if (object instanceof CustomEditorText) {
      buffer.append(((CustomEditorText) object).getCustomEditorText());
    } else {
      Bean bean = (Bean) object;
      for (Property p : visiblePropertiesOf(bean)) {
        buildPropertyRepresentation(buffer, p);
      }
    }
  }



}
