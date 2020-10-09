package org.saatsch.framework.jmmo.tools.apiclient.model;

import org.eclipse.swt.widgets.Shell;
import org.saatsch.framework.jmmo.tools.apiclient.ui.scripts.DialogWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.saatsch.framework.base.simplescript.ExecutionException;
import org.saatsch.framework.base.simplescript.JfaceStep;
import org.saatsch.framework.base.simplescript.OpenableEditor;

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
