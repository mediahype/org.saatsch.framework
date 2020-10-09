package org.saatsch.framework.jmmo.data.editor.ui.types;



import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DebugComposite extends Composite {
  private static final Logger LOG = LoggerFactory.getLogger(DebugComposite.class);

  public DebugComposite(Composite parent, int style) {
    super(parent, style);


    if (getParent().getLayout() instanceof GridLayout) {
      setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
    }

    this.addMouseTrackListener(new Mtl());
  }


}


final class Mtl implements MouseTrackListener {


  @Override
  public void mouseEnter(MouseEvent e) {
    // LOG.info("mouse enter \n-> " + me.getLayout().toString() + "\n-> " + me.getLayoutData() +
    // "\n-> " + me.getClass().getName());

  }

  @Override
  public void mouseExit(MouseEvent e) {
    //

  }

  @Override
  public void mouseHover(MouseEvent e) {
    //

  }
}
