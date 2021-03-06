package org.saatsch.framework.jmmo.data.editor.ui.composite.filterable.tree;

import java.util.List;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.swt.widgets.Composite;
import org.joda.beans.Bean;
import org.joda.beans.Property;

import org.saatsch.framework.jmmo.data.api.PropertyUtil;

public class PointerFilterableTreeComposite extends FilterableTreeComposite {

  private Property<Object> property;

  public PointerFilterableTreeComposite(Composite parent, Property<Object> property) {
    super(parent);
    this.property = property;
    init();
  }

  @Override
  protected List<?> getContentData() {


    Class<? extends Bean> c = (Class<? extends Bean>) PropertyUtil.getPointerType(property);
    
    CategorizingDataProviderBuilder builder = new CategorizingDataProviderBuilder(c);

    return builder.build();

  }

  @Override
  protected ISelectionChangedListener getSelectionChangedListener() {
    // no need to react
    return null;
  }

}
