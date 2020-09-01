package de.jmmo.data.editor.ui.composite.filterable.tree;

import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;

import de.jmmo.data.DataObject;

public class FilterableTreeContentProvider implements ITreeContentProvider{

  private FilterableTreeComposite client;

  public FilterableTreeContentProvider(FilterableTreeComposite filterableTreeComposite) {
    this.client = filterableTreeComposite;
  }

  @Override
  public Object[] getElements(Object inputElement) {
    if (client == null) {
      return new Object[0];
    }
    List<?> contentData = client.getContentData();
    if (contentData == null){
      return new Object[0];
    }
    return contentData.toArray();
  }

  @Override
  public Object[] getChildren(Object parentElement) {

    if (parentElement instanceof CategorizingDataProvider) {
      return ((CategorizingDataProvider) parentElement).getChildren().toArray();
    }
    return null;
  }

  @Override
  public Object getParent(Object element) {
    return null;
  }

  @Override
  public boolean hasChildren(Object element) {
    return !(element instanceof DataObject);
  }

}
