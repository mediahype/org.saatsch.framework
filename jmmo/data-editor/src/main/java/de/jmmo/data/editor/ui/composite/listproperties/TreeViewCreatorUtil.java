package de.jmmo.data.editor.ui.composite.listproperties;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.joda.beans.Bean;

public class TreeViewCreatorUtil {

  private TreeViewCreatorUtil() {}
  
  public static void createEditors(Composite cmpContent, Bean bean) {

    GridLayout gridLayout = (GridLayout) cmpContent.getLayout();
    gridLayout.verticalSpacing = 1;
    gridLayout.marginWidth = 1;
    gridLayout.horizontalSpacing = 1;
    gridLayout.marginHeight = 1;
    
    SashForm sashForm = new SashForm(cmpContent, SWT.NONE);
    sashForm.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

    
    AbstractPropertyTreeC treeNames = new NameTree(sashForm, bean);
    treeNames.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

    AbstractPropertyTreeC treeValues = new ValueTree(sashForm, bean);
    treeValues.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
    
    // sync tree expand and collapse
    treeValues.getTreeViewer().addTreeListener(new TreeExpansionCoordinator(treeNames.getTreeViewer()));
    treeNames.getTreeViewer().addTreeListener(new TreeExpansionCoordinator(treeValues.getTreeViewer()));
    
    // sync tree selection
    treeValues.getTreeViewer().addSelectionChangedListener(new TreeSelectionCoordinator(treeNames.getTreeViewer()));    
    treeNames.getTreeViewer().addSelectionChangedListener(new TreeSelectionCoordinator(treeValues.getTreeViewer()));    
    
  }

  
  
}
