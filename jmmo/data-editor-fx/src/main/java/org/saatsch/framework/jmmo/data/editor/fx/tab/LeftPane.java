package org.saatsch.framework.jmmo.data.editor.fx.tab;

import com.google.common.eventbus.Subscribe;
import org.joda.beans.Bean;
import org.saatsch.framework.base.jfxbase.control.VBox;
import org.saatsch.framework.jmmo.cdi.container.JmmoContext;
import org.saatsch.framework.jmmo.data.api.Pointer;
import org.saatsch.framework.jmmo.data.editor.fx.base.SelectionChanged;
import org.saatsch.framework.jmmo.data.editor.fx.composite.FilterableBeanTree;
import org.saatsch.framework.jmmo.data.editor.fx.eventing.Eventing;
import org.saatsch.framework.jmmo.data.editor.fx.eventing.NameChanged;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * the left pane on an editor tab.
 */
public class LeftPane extends VBox implements SelectionChanged<Bean> {

  private static final Logger LOG = LoggerFactory.getLogger(LeftPane.class);
  private final EditorTab parent;
  private final FilterableBeanTree beanTree;
  private final Inspect cmpInspect = new Inspect();

  private SelectionChanged<Bean> listener;

  public LeftPane(EditorTab parent) {
    this.parent = parent;
    beanTree = new FilterableBeanTree(parent.getObjectClass()).withSelectionChangedListener(this);
    withStretchedChild(beanTree).withChildren(cmpInspect);
    setId(parent.getObjectClass() + "LeftPane");
    JmmoContext.getBean(Eventing.class).register(this);
  }

  @Subscribe
  public void onNameChanged(NameChanged event) {
    // TODO
    LOG.info("Name change!");
  }

  /**
   * selects an object in the bean tree
   *
   * @param pointer the pointer to the object
   * @return true if the object was selected.
   */
  public boolean selectObject(Pointer pointer) {
    return beanTree.selectObject(pointer);
  }

  public boolean hasSelection() {
    return beanTree.getSelectionOptional().isPresent();
  }

  public void reload() {
    beanTree.reload();
  }

  public void setInspectVisible(boolean inspectMode) {
    cmpInspect.setVisible(inspectMode);
  }

  public void inspect() {
    if (cmpInspect.isVisible()) {
      cmpInspect.inspect(beanTree.getSelection());
    }
  }

  public void setListener(SelectionChanged<Bean> listener) {
    this.listener = listener;
  }


  @Override
  public void selectionChanged(Bean newSelection) {
    if (listener != null){
      listener.selectionChanged(newSelection);
    }
    cmpInspect.inspect(newSelection);
  }
}
