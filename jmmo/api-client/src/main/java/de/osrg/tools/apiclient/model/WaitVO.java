package de.osrg.tools.apiclient.model;

import org.eclipse.swt.widgets.Shell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.osrg.base.simplescript.ExecutionException;
import de.osrg.base.simplescript.JfaceStep;
import de.osrg.base.simplescript.OpenableEditor;
import de.osrg.tools.apiclient.ui.scripts.DialogWait;

public class WaitVO extends JfaceStep {

  private static final Logger LOG = LoggerFactory.getLogger(WaitVO.class);
  
  private Integer waitMillis;

  public WaitVO() {
    setName("Wait ...");
  }

  public Integer getWaitMillis() {
    return waitMillis;
  }

  public void setWaitMillis(Integer waitMillis) {
    this.waitMillis = waitMillis;
  }

  @Override
  public String toString() {
    return "WaitVO{" +
        "waitMillis=" + waitMillis +
        '}';
  }

  @Override
  public String getMoreInfo() {
    return waitMillis + " ms";
  }

  @Override
  public OpenableEditor<WaitVO> createEditDialog(Shell parent) {
    return new DialogWait(parent);
  }

  @Override
  public void execute() throws ExecutionException {
    try {
      Integer waitMillis = getWaitMillis();
      LOG.info("waiting {} millis." , waitMillis);
      Thread.sleep(waitMillis);
    } catch (InterruptedException e) {
      throw new ExecutionException(e.getMessage(), e);
    }
    
  }


}
