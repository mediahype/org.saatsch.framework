package org.saatsch.framework.jmmo.data.editor.action;

import java.util.Optional;
import java.util.Set;

import org.joda.beans.Bean;

import org.saatsch.framework.jmmo.cdi.container.JmmoContext;
import org.saatsch.framework.jmmo.data.DataSink;
import org.saatsch.framework.jmmo.data.action.IAction;
import org.saatsch.framework.jmmo.data.api.BeanReference;
import org.saatsch.framework.jmmo.data.api.IntlStringService;
import org.saatsch.framework.jmmo.data.api.PropertyUtil;
import org.saatsch.framework.jmmo.data.api.beans.BeanService;
import org.saatsch.framework.jmmo.data.mongo.ModelService;

/**
 * changes the appId of a Bean. This is a 3 part process.
 * <ul>
 * <li>changes the coordinates of intl. String the Bean uses.</li>
 * <li>changes the incoming Pointers to the Bean</li>
 * <li>changes the appId of the Bean</li>
 * </ul>
 * 
 * @author saatsch
 *
 */
public class ChangeAppIdAction implements IAction {

  /**
   * the Bean whose appId is to be changed.
   */
  private Bean bean;
  
  /**
   * the new appId 
   */
  private String newAppId;

  /**
   * the old appId
   */
  private String oldAppId;

  public ChangeAppIdAction(Bean bean, String newAppId, String oldAppId) {
    this.bean = bean;
    this.newAppId = newAppId;
    this.oldAppId = oldAppId;
  }
   
  @Override
  public Object execute() {
    
    if (newAppId.equals(oldAppId)) {
      return Optional.empty();
    }
    
    // Step 1:
    IntlStringService stringService = JmmoContext.getBean(IntlStringService.class);
    stringService.changeIntlStringCoordinate(bean, newAppId);
    
    // Step 2:
    Set<BeanReference> references = JmmoContext.getBean(ModelService.class).resolveIncomingReferences(bean);
    
    for (BeanReference br : references) { 
      updateIncomingRefs(br.getBean());  
    }

    // Step 3:
    PropertyUtil.getAppIdProperty(bean).set(newAppId);
    DataSink data = JmmoContext.getBean(DataSink.class);
    data.save(bean);

    
    
    
    return bean;
  }

  private void updateIncomingRefs(Bean b) {
    
    BeanService beanService = JmmoContext.getBean(BeanService.class);
    beanService.findPointers(b).forEach(pointer -> {
      
      if (pointer.getAppId().equals(oldAppId)) {
        pointer.setTargetCoodinate(pointer.getBaseClass(), newAppId);
      }
      
    });
    JmmoContext.getBean(DataSink.class).save(b);
  }

}
