package org.saatsch.framework.jmmo.data.action;

import java.util.List;

import org.joda.beans.Bean;

import org.saatsch.framework.jmmo.cdi.container.JmmoContext;
import org.saatsch.framework.jmmo.data.DataSink;
import org.saatsch.framework.jmmo.data.api.IntlStringService;
import org.saatsch.framework.jmmo.data.api.beans.BeanService;
import org.saatsch.framework.jmmo.data.impl.BeanAndProperty;

/**
 * upon executing this IAction it deletes an Object from the database.
 * 
 * @author saatsch
 *
 */
public class DeleteObjectAction implements IAction {

  private Bean toDelete;

  public DeleteObjectAction(Bean toDelete) {
    this.toDelete = toDelete;
  }

  public Object execute() {
    BeanService beanService = JmmoContext.getBean(BeanService.class);
    IntlStringService stringService = JmmoContext.getBean(IntlStringService.class);
    
    List<BeanAndProperty> strings = beanService.findIntlStrings(toDelete);
    for (BeanAndProperty bap : strings){
      stringService.delete(bap.getBean(), bap.getProp());
    }
    
    JmmoContext.getBean(DataSink.class).delete(toDelete);
    
    return null;
  }

}
