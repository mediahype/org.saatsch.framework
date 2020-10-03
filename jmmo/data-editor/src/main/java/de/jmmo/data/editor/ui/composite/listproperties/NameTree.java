package de.jmmo.data.editor.ui.composite.listproperties;

import org.eclipse.swt.widgets.Composite;
import org.joda.beans.Bean;

public class NameTree extends AbstractPropertyTreeC {

  public NameTree(Composite parent, Bean object) {
    super(parent, object);


    treeViewer.setLabelProvider(new NameProvider());
    treeViewer.setInput(object);
    

  }



}
