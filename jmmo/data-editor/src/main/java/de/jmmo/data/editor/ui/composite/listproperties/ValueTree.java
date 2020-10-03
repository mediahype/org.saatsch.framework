package de.jmmo.data.editor.ui.composite.listproperties;

import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.widgets.Composite;
import org.joda.beans.Bean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.jmmo.cdi.container.JmmoContext;
import de.jmmo.data.editor.ui.dialog.EditPropertyDialog;
import de.jmmo.data.mongo.MorphiaMongoDataSink;

/**
 * composite wrapping a {@link TreeViewer} that displays values of a given input {@link Bean}.
 * 
 * This must support copy/paste operations and double clicks must open a dialog to edit
 * sub-properties/nested beans.
 * 
 * @author saatsch
 *
 */
public class ValueTree extends AbstractPropertyTreeComposite {

  private static final Logger LOG = LoggerFactory.getLogger(ValueTree.class);

  public ValueTree(Composite parent, Bean object) {
    super(parent, object);

    treeViewer.setLabelProvider(new ValueProvider());
    treeViewer.setInput(object);

    treeViewer.getTree().addKeyListener(new KeyListener() {

      @Override
      public void keyReleased(KeyEvent e) {}

      @Override
      public void keyPressed(KeyEvent e) {
        if ((e.stateMask & SWT.CONTROL) != 0 && e.keyCode == 99) {
          LOG.info("control+c pressed ");
          doCopy();
        }
        if ((e.stateMask & SWT.CONTROL) != 0 && e.keyCode == 118) {
          LOG.info("control+v pressed ");
          doPaste();
        }
      }
    });

    treeViewer.addDoubleClickListener(new IDoubleClickListener() {
      
      @Override
      public void doubleClick(DoubleClickEvent event) {
        // 
        EditPropertyDialog diag = new EditPropertyDialog(getShell(), object, selected());
        diag.open();
        treeViewer.refresh();
        
      }
    });
    

  }

  protected void doPaste() {
    
    if (!canBePasted()){
      return;
    }

    // FIXME: use a deep clone of buffered.
    selected().set(buffered().get());
    LOG.info("pasted!");
    // TODO: wrap this save in an action.
    JmmoContext.getBean(MorphiaMongoDataSink.class).save(object);
    
    treeViewer.refresh();
    
  }




}
