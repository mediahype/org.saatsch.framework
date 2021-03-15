package org.saatsch.framework.jmmo.data.editor.fx.beantree;

import java.util.Optional;
import javafx.collections.ListChangeListener;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import org.joda.beans.Bean;
import org.saatsch.framework.jmmo.data.editor.fx.base.SelectionChanged;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * a {@link TreeView} of {@link Bean}s. Informs an optional {@link #listener} when a selection
 * change occurs.
 */
public class BeanTree extends TreeView<Bean> {

  private static final Logger LOG = LoggerFactory.getLogger(BeanTree.class);

  private Optional<SelectionChanged> listener = Optional.empty();

  public BeanTree() {

    setShowRoot(false);
    setRoot(new TreeItem("root"));
    setCellFactory(param -> new BeanTreeCell());

    getSelectionModel().getSelectedItems()
        .addListener((ListChangeListener<TreeItem<Bean>>) change -> {
          Bean newSelection = change.getList().get(0).getValue();
          listener.ifPresent(l -> l.selectionChanged(newSelection));
        });


  }

  /**
   * sets a listener to inform when the selection has changed.
   *
   * @param listener the listener to set. Can be null (to stop listening).
   */
  public void setSelectionChangedListener(SelectionChanged<Bean> listener) {
    this.listener = Optional.ofNullable(listener);
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

}
