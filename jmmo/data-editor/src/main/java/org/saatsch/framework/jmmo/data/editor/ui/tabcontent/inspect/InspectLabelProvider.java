package org.saatsch.framework.jmmo.data.editor.ui.tabcontent.inspect;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.joda.beans.Bean;

import org.saatsch.framework.jmmo.data.api.BeanReference;
import org.saatsch.framework.jmmo.data.api.PropertyUtil;
import org.saatsch.framework.jmmo.data.editor.Images;
import org.saatsch.framework.jmmo.data.editor.Img;
import org.saatsch.framework.jmmo.data.editor.ui.UiUtils;

public class InspectLabelProvider extends LabelProvider {

  @Override
  public String getText(Object element) {

    if (element instanceof Bean) {
      return UiUtils.emptyOrString(PropertyUtil.getFullName((Bean) element));
    }
    
    if (element instanceof BeanReference) {
      return UiUtils.emptyOrString(PropertyUtil.getFullName(   ((BeanReference) element).getBean()  ));
    }
    
    
    return super.getText(element);
  }
  
  @Override
  public Image getImage(Object element) {
    
    if (element instanceof BeanReference) {
      switch (((BeanReference) element).getDirection()) {
        case INCOMING:
          return Images.get(Img.INCOMING_REF);
        case OUTGOING:
          return Images.get(Img.OUTGOING_REF);
        default:
          return null;
      }
    }

    return null;

  }
  
}
