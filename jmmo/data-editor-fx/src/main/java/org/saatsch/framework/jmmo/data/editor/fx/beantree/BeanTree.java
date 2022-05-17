package org.saatsch.framework.jmmo.data.editor.fx.beantree;

import java.util.Optional;
import javafx.collections.ListChangeListener;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import org.joda.beans.Bean;
import org.joda.beans.MetaProperty;
import org.saatsch.framework.jmmo.data.api.Pointer;
import org.saatsch.framework.jmmo.data.api.PropertyUtil;
import org.saatsch.framework.jmmo.data.editor.fx.base.SelectionChanged;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * a {@link TreeView} of {@link Bean}s.
 * change occurs.
 */
public class BeanTree extends TreeView<Bean> {

  private static final Logger LOG = LoggerFactory.getLogger(BeanTree.class);

  private SelectionChanged<Bean> listener;

  public BeanTree() {

    setShowRoot(false);
    setRoot(new TreeItem("root"));
    setCellFactory(param -> new BeanTreeCell());

    getSelectionModel().getSelectedItems()
        .addListener((ListChangeListener<TreeItem<Bean>>) change -> {
          if (listener != null){
            Bean newSelection = change.getList().get(0).getValue();
            listener.selectionChanged(newSelection);
          }
        });
  }

  /**
   * sets a listener to inform when the selection has changed.
   *
   * @param listener the listener to set. Can be null (to stop listening).
   * @return this.
   */
  public BeanTree withSelectionChangeListener(SelectionChanged<Bean> listener) {
    this.listener= listener;
    return this;
  }

  /**
   * @return the currently selected Bean or null if nothing is selected.
   */
  public Bean getSelection() {
    if (getSelectionModel().getSelectedItem() != null) {
      return getSelectionModel().getSelectedItem().getValue();
    }
    return null;
  }

  /**
   * selects an object in the bean tree
   *
   * @param pointer the pointer to the object
   * @return true if the object was selected.
   */
  public boolean selectObject(Pointer pointer) {

    Optional<TreeItem<Bean>> beanTreeItem = getRoot().getChildren().stream()
        .filter(item -> appIdFilter(pointer, item)).findFirst();

    if (beanTreeItem.isPresent()){
      getSelectionModel().select(beanTreeItem.get());
      return true;

    }

    return false;

  }

  private boolean appIdFilter(Pointer pointer, TreeItem<Bean> item) {
    Bean bean = item.getValue();

    MetaProperty<?> appIdProperty = PropertyUtil.getAppIdProperty(bean.getClass());
    if (appIdProperty == null) {
      return false;
    }

    return appIdProperty.get(bean).equals(pointer.getAppId());
  }


}
