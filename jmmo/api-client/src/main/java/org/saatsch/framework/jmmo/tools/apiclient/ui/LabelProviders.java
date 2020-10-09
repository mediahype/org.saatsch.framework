package org.saatsch.framework.jmmo.tools.apiclient.ui;

import org.eclipse.jface.viewers.LabelProvider;

import org.saatsch.framework.jmmo.tools.apiclient.model.MethodCallVO;

public class LabelProviders {

  /**
   * {@link LabelProvider} for {@link MethodCallVO}s.
   */
  public static final LabelProvider CALLS_LP = new LabelProvider() {

    @Override
    public String getText(Object element) {
      if (element instanceof MethodCallVO) {
        return ((MethodCallVO) element).getName();
      }

      return super.getText(element);
    }

  };


  public static final LabelProvider APIS_LP = new LabelProvider() {
    @Override
    public String getText(Object element) {
      if (element instanceof Folder) {
        return ((Folder) element).getName();
      }

      if (element instanceof AbstractMethod) {
        return ((AbstractMethod) element).getName() + " - "
            + ((AbstractMethod) element).getDeclaredIn().getSimpleName();
      }

      return super.getText(element);
    }
  };

}
