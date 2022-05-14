package org.saatsch.framework.jmmo.data.editor.fx.tab;

import com.google.common.eventbus.Subscribe;
import javafx.scene.layout.VBox;
import org.joda.beans.Bean;
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
public class LeftPane extends VBox {

  private static final Logger LOG = LoggerFactory.getLogger(LeftPane.class);
  private final EditorTab parent;
  private final FilterableBeanTree beanTree;

  public LeftPane(EditorTab parent){
    this.parent = parent;
    beanTree = new FilterableBeanTree(parent.getObjectClass());
    getChildren().add(beanTree);
    setId(parent.getObjectClass() + "LeftPane");
    JmmoContext.getBean(Eventing.class).register(this);
  }


  public void setSelectionChangedListener(SelectionChanged<Bean> listener) {
    beanTree.setSelectionChangedListener(listener);
  }

  @Subscribe
  public void onNameChanged(NameChanged event){
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

  public boolean hasSelection(){
    return beanTree.getSelectionOptional().isPresent();
  }

}
