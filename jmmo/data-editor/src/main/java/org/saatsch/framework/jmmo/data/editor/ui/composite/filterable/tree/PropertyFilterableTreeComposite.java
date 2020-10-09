package org.saatsch.framework.jmmo.data.editor.ui.composite.filterable.tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.swt.widgets.Composite;
import org.joda.beans.Property;

import org.saatsch.framework.jmmo.cdi.container.JmmoContext;
import org.saatsch.framework.jmmo.data.DataSink;
import org.saatsch.framework.jmmo.data.api.PropertyUtil;

/**
 * given a {@link Property} (which must be a {@link List} with a generic type argument) extracts the
 * generic type and requests all instances of this type from the database. Puts the result into a
 * filterable ui-tree.
 * 
 * @author saatsch
 *
 */
public class PropertyFilterableTreeComposite extends FilterableTreeComposite {

  private Property<Object> property;

  /**
   * creates a new {@link PropertyFilterableTreeComposite}
   * 
   * @param parent the parent {@link Composite}
   * @param property the {@link Property}
   */
  public PropertyFilterableTreeComposite(Composite parent, Property<Object> property) {
    super(parent);
    this.property = property;
    init();
  }

  @Override
  protected List<?> getContentData() {
    if (null != property) {

      DataSink data = JmmoContext.getBean(DataSink.class);
      if (null != data) {
        return data.getAll(PropertyUtil.firstTypeArgRecurse(property.metaProperty()));
      }

    }
    return Collections.unmodifiableList(new ArrayList<>());
  }

  @Override
  protected ISelectionChangedListener getSelectionChangedListener() {
    // no need to react
    return null;
  }

}
