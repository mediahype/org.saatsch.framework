package de.jmmo.data.editor.ui.dialog;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.joda.beans.Bean;
import org.joda.beans.Property;

import de.jmmo.cdi.container.JmmoContext;
import de.jmmo.data.DataSink;
import de.jmmo.data.editor.ui.GridView;
import de.jmmo.data.editor.ui.types.EditorCreatorUtil;

/**
 * a dialog that can be populated to edit an arbitrary property
 * 
 * @author saatsch
 *
 */
public class EditorWrapperDialog extends Dialog {

  private Shell shell;
  private Property<Object> propertyToEdit;
  private Bean objectToEdit;

  private ScrolledComposite scrolledComposite;

  /**
   * the composite into which the editor content goes. it has a grid layout.
   */
  private Composite cmpContent;

  public EditorWrapperDialog(Shell parent, Bean objectToEdit, Property<Object> propertyToEdit) {
    super(parent);
    this.objectToEdit = objectToEdit;
    this.propertyToEdit = propertyToEdit;
  }

  public Object open() {
    createContents();
    shell.open();
    shell.layout();
    Display display = getParent().getDisplay();
    while (!shell.isDisposed()) {
      if (!display.readAndDispatch()) {
        display.sleep();
      }
    }
    return null;
  }

  private void createContents() {
    shell = new Shell(getParent(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL| SWT.RESIZE);
    shell.setSize(595, 372);
    shell.setText("Edit");
    shell.setLayout(new FillLayout(SWT.HORIZONTAL));

    // TODO: use an Action for this.
    shell.addDisposeListener(e -> JmmoContext.getBean(DataSink.class).save(objectToEdit));

    scrolledComposite = new ScrolledComposite(shell, SWT.H_SCROLL | SWT.V_SCROLL);
    scrolledComposite.addControlListener(new ControlAdapter() {
      @Override
      public void controlResized(ControlEvent e) {
        calcSize();
      }
    });
    scrolledComposite.setExpandVertical(true);
    scrolledComposite.setExpandHorizontal(true);
    scrolledComposite.setMinSize(new Point(200, 200));

    cmpContent = new GridView(scrolledComposite);
    scrolledComposite.setContent(cmpContent);


    EditorCreatorUtil.createEditorForField(cmpContent, propertyToEdit, objectToEdit);

  }

  void calcSize() {
    int height = 0;
    for (Control control : cmpContent.getChildren()) {
      if (control instanceof Composite) {
        height = height + control.getSize().y;
      }
    }
    scrolledComposite.setMinSize(200, height);

  }

}
