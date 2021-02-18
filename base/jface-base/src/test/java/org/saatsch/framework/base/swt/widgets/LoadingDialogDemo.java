package org.saatsch.framework.base.swt.widgets;

import org.eclipse.swt.widgets.Shell;
import org.junit.Test;
import org.saatsch.framework.base.swt.MinimalParentShell;
import org.saatsch.framework.base.swt.Openable;

public class LoadingDialogDemo extends MinimalParentShell implements Openable<Void> {

 
  @Test
  public void test() {

    LoadingDialogDemo testedClass = new LoadingDialogDemo();
    testedClass.open();

  }

  @Override
  public Openable<Void> init(Shell parentShell) {

    return new Loading(parentShell);

  }

}
