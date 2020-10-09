package org.saatsch.framework.base.swt.event;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Shell;
import org.saatsch.framework.base.swt.MessageBoxUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Abstract selection event Listener that catches Exceptions. The exception message is shown in a message box
 * 
 * @author saatsch,
 * @version
 */
public abstract class AExceptionCatchingSelectionAdapter extends SelectionAdapter {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Override
    public void widgetSelected(SelectionEvent e) {

        try {
            selected(e);
        } catch (Exception ex) {
            MessageBoxUtil.showErrorMessage(ex.getMessage(), getShell());
        }

    }

    public abstract void selected(SelectionEvent e);
    
    public abstract Shell getShell();

}
