package de.jmmo.data.editor.ui.tabcontent;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.joda.beans.Bean;

import de.jmmo.data.editor.ui.composite.filterable.tree.TabContentCallback;
import de.jmmo.data.editor.ui.tabcontent.inspect.InspectComposite;

public class TabLeftComposite extends Composite {

  private TabFilterableTreeComposite cmpObjects;
  private InspectComposite cmpInspect;

  public TabLeftComposite(Composite parent, TabContentCallback tabContent) {
    super(parent, SWT.NONE);
    setLayout(new FillLayout(SWT.HORIZONTAL));

    SashForm sashForm = new SashForm(this, SWT.VERTICAL);

    cmpObjects = new TabFilterableTreeComposite(sashForm, tabContent);
    GridLayout gridLayout = (GridLayout) cmpObjects.getLayout();
    gridLayout.marginWidth = 1;
    gridLayout.verticalSpacing = 2;
    gridLayout.horizontalSpacing = 2;
    gridLayout.marginHeight = 2;

    cmpInspect = new InspectComposite(sashForm);
    sashForm.setWeights(new int[] {2, 1});
  }

  public TabFilterableTreeComposite getObjectTree() {
    return cmpObjects;
  }

  public void inspect(Bean bean, Class baseClass) {
    if (bean != null) {
      cmpInspect.inspect(bean, baseClass);      
    }
  }

  public void setInspectVisible(boolean inspectMode) {
    cmpInspect.setVisible(inspectMode);
    cmpInspect.requestLayout();
  }

}
