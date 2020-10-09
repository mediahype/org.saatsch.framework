package org.saatsch.framework.jmmo.data.editor.ui.tabcontent;

/*
 * #%L nof-edit $Id:$ $HeadURL:$ %% Copyright (C) 2013 http://nof.sf.net %% Licensed under the
 * Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License. #L%
 */

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.joda.beans.Bean;
import org.saatsch.framework.jmmo.data.editor.ui.composite.filterable.tree.TabContentCallback;
import org.saatsch.framework.jmmo.data.editor.ui.composite.listproperties.TreeViewCreatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.saatsch.framework.jmmo.data.editor.ui.GridView;
import org.saatsch.framework.jmmo.data.editor.ui.types.EditorCreatorUtil;


/**
 * The content of a tab in the editor. It is aware of the Class of objects it should manage. It has
 * a cmpLeft, a Composite that contains the object tree and a cmpContent, a Composite that contains
 * the view of the currently selected object.
 * 
 * @author saatsch
 * 
 */
public class EditorTabContent extends Composite implements TabContentCallback {

  private static final Logger LOG = LoggerFactory.getLogger(EditorTabContent.class);
  private final Class<? extends Bean> objectClass;
  private final ScrolledComposite scrolledComposite;

  private boolean inspectMode = false;

  /**
   * the composite that displays the list of objects in this tab
   */
  private TabLeftComposite cmpLeft;

  /**
   * the composite into which the editor content goes. it has a grid layout.
   */
  private Composite cmpContent;

  private boolean editModeClassic = true;

  /**
   * Create the composite.
   * 
   * @param parent
   * @param style
   * @param oc
   * 
   */
  public EditorTabContent(Composite parent, int style, Class objectClass) {
    super(parent, style);
    this.objectClass = objectClass;
    setLayout(new FillLayout(SWT.HORIZONTAL));

    SashForm sashForm = new SashForm(this, SWT.NONE);

    cmpLeft = new TabLeftComposite(sashForm, this);

    scrolledComposite = new ScrolledComposite(sashForm, SWT.H_SCROLL | SWT.V_SCROLL);
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

    sashForm.setWeights(new int[] {139, 345});

    calcSize();

    drawInspect();


  }

  /**
   * draws the inspect component. Does nothing if no object is selected. 
   */
  private void drawInspect() {
    cmpLeft.setInspectVisible(inspectMode);
    if (inspectMode) {
      cmpLeft.inspect(getCurrentlySelected(), objectClass);
    }
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

  @Override
  protected void checkSubclass() {
    // Disable the check that prevents subclassing of SWT components
  }

  /**
   * fills the object tree of this {@link EditorTabContent} with appropriate data.
   */
  public void fill() {
    cmpLeft.getObjectTree().refresh();
  }


  /**
   * add a popup menu to the tree in the tab content.
   * 
   * @param shell the parent {@link Shell}
   */
  public void addMenu(Shell shell) {
    Menu menu = new ObjectContextMenu(shell, this);
    cmpLeft.getObjectTree().setContextMenu(menu);
  }

  /**
   * returns the {@link Bean} that is currently selected or <code>null</code> if no item is
   * selected.
   * 
   * @return the {@link Bean} that is currently selected or <code>null</code> if no item is
   *         selected.
   */
  public Bean getCurrentlySelected() {
    if (cmpLeft.getObjectTree().getCurrentlySelected() instanceof Bean) {
      return (Bean) cmpLeft.getObjectTree().getCurrentlySelected();
    }
    return null;
  }

  public Class<? extends Bean> getObjectClass() {
    return objectClass;
  }

  public void toggleEditMode() {
    editModeClassic = !editModeClassic;
    repaintContent(getCurrentlySelected());
  }

  public void toggleInspect() {
    inspectMode = !inspectMode;
    drawInspect();
  }

  public void contentDisposeChildren() {
    for (Control c : cmpContent.getChildren()) {
      c.dispose();
    }
  }

  public void createContent(Bean bean) {
    if (editModeClassic) {
      cmpContent = new GridView(scrolledComposite);
      scrolledComposite.setContent(cmpContent);
      EditorCreatorUtil.createEditors(cmpContent, bean, bean);
    } else {
      TreeViewCreatorUtil.createEditors(cmpContent, bean);
    }

  }

  public void repaintContent(Bean bean) {
    contentDisposeChildren();
    createContent(bean);
    calcSize();
    cmpContent.requestLayout();

    if (inspectMode) {
      // paint inspection List, if active
      cmpLeft.inspect(bean, objectClass);
    }

  }

  /**
   * repaints complete tab content.
   */
  public void repaintAll() {
    // TODO: save object that was selected and re-select it after repaint.
    Bean selected = getCurrentlySelected();
    LOG.info("selected: {}", selected);
    contentDisposeChildren();
    redraw();
    fill();
    
    if (null != selected){
      select(selected);      
    }
    
  }

  

  public void select(Bean bean) {
    cmpLeft.getObjectTree().makeSelected(bean);
  }

}
