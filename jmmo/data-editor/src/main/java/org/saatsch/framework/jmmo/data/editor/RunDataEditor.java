package org.saatsch.framework.jmmo.data.editor;

import org.saatsch.framework.jmmo.data.api.beans.BeanService;
import org.saatsch.framework.jmmo.data.impl.BeanServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.saatsch.framework.jmmo.cdi.container.JmmoContext;

/**
 * main entry point for consumers of this library.
 * 
 * @author saatsch
 *
 */
public class RunDataEditor {
  private static final Logger LOG = LoggerFactory.getLogger(RunDataEditor.class);

  /**
   * Launch the application.
   * 
   * @param args
   */
  public static void main(String[] args) {

    JmmoContext.alias(BeanService.class, BeanServiceImpl.class);
    
    try {

      MainGui window = new MainGui();
      JmmoContext.putBean(window);

      window.open();
    } catch (Exception e) {
      LOG.error("Error: ", e);
    }


  }

}
