package de.jmmo.data.editor.ui.composite.listproperties;

import static de.jmmo.data.api.PropertyUtil.getJmmoDoc;

import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ViewerCell;
import org.joda.beans.Property;

/**
 * tooltips for the treeviewers are not yet working. 
 * 
 * @author saatsch
 *
 */
public class NameTooltipProvider extends CellLabelProvider{
  
  @Override
  public String getToolTipText(Object element) {
    
    if (element instanceof Property) {
      return getJmmoDoc((Property)element);
    }
    
    return null;
  }

  @Override
  public void update(ViewerCell cell) {
    // Do nothing
    
  }
}

