package de.jmmo.data.editor.ui.composite.filterable;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.joda.beans.Bean;

import de.jmmo.data.api.PropertyUtil;
import de.jmmo.data.editor.ui.composite.filterable.tree.CategorizingDataProvider;

public class JmmoBeanFilter extends ViewerFilter {

  private String searchString;

  public void setSearchText(String s) {

    this.searchString = s;
  }

  @Override
  public boolean select(Viewer viewer, Object parentElement, Object element) {

    if (searchString == null || searchString.length() == 0) {
      return true;
    }

    return checkElementAndChildren(element);

  }

  private boolean checkElementAndChildren(Object element) {
    
    if (element instanceof CategorizingDataProvider) {
      for (Object dp : ((CategorizingDataProvider) element).getChildren() ) {
        if (checkElementAndChildren(dp)) {
          return true;
        }
      }
    }
    
    if (element instanceof Bean) {
      String fullName = PropertyUtil.getFullName((Bean) element);
      if (null == fullName) {
        return true;
      }
      return fullName.contains(searchString);
    }
    return false;
  }
  
}
