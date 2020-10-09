package org.saatsch.framework.jmmo.data.editor.ui.tabcontent;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.swt.widgets.Composite;

import org.saatsch.framework.jmmo.data.editor.ui.composite.filterable.tree.CategorizingDataProviderBuilder;
import org.saatsch.framework.jmmo.data.editor.ui.composite.filterable.tree.FilterableTreeComposite;
import org.saatsch.framework.jmmo.data.editor.ui.composite.filterable.tree.TabContentCallback;

/**
 * FilterableList for use in an object tab. This variant knows the tab and populates the list with
 * objects of the objectClass that is assigned to the tab.
 * 
 * @author saatsch
 *
 */
public class TabFilterableTreeComposite extends FilterableTreeComposite {

  private final TabContentCallback tabContent;


  public TabFilterableTreeComposite(Composite parent, TabContentCallback tabContent) {
    super(parent);
    this.tabContent = tabContent;
    init();

  }

  @Override
  protected List<?> getContentData() {

    if (null != tabContent){
      CategorizingDataProviderBuilder builder =
          new CategorizingDataProviderBuilder(tabContent.getObjectClass());
      
      return builder.build();      
    }
    return new ArrayList<>();

  }

  
  /** 
   * {@inheritDoc}
   */
  @Override
  protected ISelectionChangedListener getSelectionChangedListener() {
    return new ObjectSelectionAdapter(tabContent);
  }

}
