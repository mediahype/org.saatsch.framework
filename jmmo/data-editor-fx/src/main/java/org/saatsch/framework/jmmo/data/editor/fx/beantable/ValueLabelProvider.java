package org.saatsch.framework.jmmo.data.editor.fx.beantable;

import static org.saatsch.framework.jmmo.data.api.PropertyUtil.isCollectionOfPointers;
import static org.saatsch.framework.jmmo.data.api.PropertyUtil.isIntlString;
import static org.saatsch.framework.jmmo.data.api.PropertyUtil.isSupportedCollection;
import static org.saatsch.framework.jmmo.data.api.PropertyUtil.visiblePropertiesOf;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TreeTableColumn.CellDataFeatures;
import javafx.util.Callback;
import org.joda.beans.Bean;
import org.joda.beans.Property;
import org.saatsch.framework.jmmo.cdi.container.JmmoContext;
import org.saatsch.framework.jmmo.data.api.CustomEditorText;
import org.saatsch.framework.jmmo.data.api.IntlStringService;
import org.saatsch.framework.jmmo.data.api.PropertyUtil;

public class ValueLabelProvider implements
    Callback<CellDataFeatures<Object, String>, ObservableValue<String>> {

  @Override
  public ObservableValue<String> call(CellDataFeatures<Object, String> param) {

    if (param.getValue().getValue() == null){
      return null;
    }

    Object value = param.getValue().getValue();

    if (value instanceof Property){

      Property<Object> p = (Property) value;

      if (p.get() instanceof Bean) {
        return new SimpleStringProperty( buildForBean(p));
      } else if (isSupportedCollection(p)) {
        return new SimpleStringProperty(buildForCollection(p));
      } else if (isIntlString(p)) {
        return new SimpleStringProperty(JmmoContext.getBean(IntlStringService.class).loadLocalizedText((String) p.get()));
      } else {
        return new SimpleStringProperty(p.get().toString());
      }

    }

    return null;

  }

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
