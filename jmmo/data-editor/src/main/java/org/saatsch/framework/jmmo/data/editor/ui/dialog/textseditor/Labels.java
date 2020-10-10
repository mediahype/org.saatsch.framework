package org.saatsch.framework.jmmo.data.editor.ui.dialog.textseditor;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import org.saatsch.framework.jmmo.cdi.container.JmmoContext;
import org.saatsch.framework.jmmo.data.api.DataConfig;
import org.saatsch.framework.jmmo.data.api.model.IntlString;

/**
 * Label Provider for the main table in {@link IntlStringsDialog}.
 * 
 * @author saatsch
 *
 */
class Labels extends LabelProvider implements ITableLabelProvider{

  private DataConfig cfg = JmmoContext.getBean(DataConfig.class);
  
  @Override
  public Image getColumnImage(Object element, int columnIndex) {
    return null;
  }

  @Override
  public String getColumnText(Object element, int columnIndex) {
    
    if (element instanceof IntlString) {
      
      switch (columnIndex) {
        case 0:
          return ((IntlString) element).getForLanguage(cfg.getCurrentLanguage());  
        case 1:
          return ((IntlString) element).getCoordinate();
        case 2:
          return ((IntlString) element).getStrings().keySet().toString();
        default:
          return "unknown column";

      }
            
    }

    return "";
  }

  
  
}
