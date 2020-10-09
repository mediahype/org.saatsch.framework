package org.saatsch.framework.jmmo.tools.apiclient.ui.expressions;

import java.util.Set;

import org.eclipse.jface.viewers.ITreeContentProvider;

public class ExpressionsContentProvider implements ITreeContentProvider {

  @Override
  public Object[] getElements(Object inputElement) {
    // the input element is the set of keys in the ExpressionLanguage
    return ((Set)inputElement).toArray();
  }

  @Override
  public Object[] getChildren(Object parentElement) {
    return null;
  }

  @Override
  public Object getParent(Object element) {
    return null;
  }

  @Override
  public boolean hasChildren(Object element) {
    return false;
  }

}
