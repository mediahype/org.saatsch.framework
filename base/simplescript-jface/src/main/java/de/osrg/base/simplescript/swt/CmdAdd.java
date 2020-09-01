package de.osrg.base.simplescript.swt;

import java.lang.reflect.InvocationTargetException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.osrg.base.simplescript.JfaceStep;
import de.osrg.base.simplescript.OpenableEditor;
import de.osrg.base.simplescript.SimpleStep;

public class CmdAdd extends AbstractAddToScript {

  private static final Logger LOG = LoggerFactory.getLogger(CmdAdd.class);
  
  private Class<? extends JfaceStep> type;

  public CmdAdd(ScriptsUI scriptsUI, Class<? extends JfaceStep> type) {
    super(scriptsUI);
    this.type = type;
  }

  @Override
  protected SimpleStep showDialog() {
    
    try {
       JfaceStep step = type.getDeclaredConstructor().newInstance();
       
       OpenableEditor dialog = step.createEditDialog(client.getShell());
       return (SimpleStep) dialog.open();
       
    } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
        | InvocationTargetException | NoSuchMethodException | SecurityException e) {
      LOG.error("Error: ", e);
    }
    

    return null;
    
    
  }

}
