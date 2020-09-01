package de.osrg.tools.apiclient.ui;

import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import de.osrg.tools.apiclient.model.MethodCallVO;

public class CallsContentProvider implements ITreeContentProvider {

  public void dispose() {

  }

  public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {}

  public Object[] getElements(Object inputElement) {
    return ((List<MethodCallVO>) inputElement).toArray();
  }

  public Object[] getChildren(Object parentElement) {
    return new Object[0];
  }

  public Object getParent(Object element) {
    return null;
  }

  public boolean hasChildren(Object element) {
    return false;
  }

}
