package de.jmmo.data.editor.ui.composite.filterable;

import org.eclipse.jface.viewers.LabelProvider;
import org.joda.beans.Bean;

import de.jmmo.data.api.PropertyUtil;
import de.jmmo.data.editor.ui.UiUtils;
import de.jmmo.data.editor.ui.composite.filterable.tree.CategorizingDataProvider;

public class JmmoBeanLabelProvider extends LabelProvider {


  @Override
  public String getText(Object element) {
    
    if (element instanceof Bean) {
      return UiUtils.emptyOrString(PropertyUtil.getFullName((Bean) element));
    }

    if (element instanceof CategorizingDataProvider) {
      return UiUtils.emptyOrString(((CategorizingDataProvider) element).getLabel());
    }
    
    return super.getText(element);
  }
  


}
