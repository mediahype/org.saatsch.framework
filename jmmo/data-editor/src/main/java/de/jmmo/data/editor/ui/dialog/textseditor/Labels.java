package de.jmmo.data.editor.ui.dialog.textseditor;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import de.jmmo.cdi.container.JmmoContext;
import de.jmmo.data.api.DataConfig;
import de.jmmo.data.api.model.IntlString;

public class Labels extends LabelProvider implements ITableLabelProvider{

  private DataConfig cfg = JmmoContext.getBean(DataConfig.class);
  
  @Override
  public Image getColumnImage(Object element, int columnIndex) {
    return null;
  }

  @Override
  public String getColumnText(Object element, int columnIndex) {
    
    if (element instanceof IntlString) {
      if (columnIndex == 0) {
        return ((IntlString) element).getStrings().get(cfg.getCurrentLanguage());
      }
      if (columnIndex == 1) {
        return ((IntlString) element).getCoordinate();
      }
    }

    return "";
  }

  
  
}
