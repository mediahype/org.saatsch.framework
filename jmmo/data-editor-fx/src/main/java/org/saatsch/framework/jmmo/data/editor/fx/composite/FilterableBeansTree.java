package org.saatsch.framework.jmmo.data.editor.fx.composite;

import javafx.collections.ObservableList;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.Priority;
import org.joda.beans.Bean;
import org.saatsch.framework.base.jfxbase.control.VBox;
import org.saatsch.framework.jmmo.cdi.container.JmmoContext;
import org.saatsch.framework.jmmo.data.DataSink;
import org.saatsch.framework.jmmo.data.api.Pointer;
import org.saatsch.framework.jmmo.data.editor.fx.base.SelectionChanged;
import org.saatsch.framework.jmmo.data.editor.fx.beantree.BeansTree;
import org.saatsch.framework.jmmo.data.editor.fx.tab.LeftPaneContextMenu;

import java.util.Optional;

public class FilterableBeansTree extends VBox implements SelectionChanged<Bean> {

  private final BeansTree beanTree = new BeansTree();
  private final TextField txtFilter = new TextField();
  private final ObservableList<TreeItem<Bean>> uiList = beanTree.getRoot().getChildren();
  private final DataSink data = JmmoContext.getBean(DataSink.class);
  private final Class<? extends Bean> clazz;

  private SelectionChanged<Bean> selectionChangedListener;

  public FilterableBeansTree(Class<? extends Bean> clazz) {
    this.clazz = clazz;
    VBox.setVgrow(beanTree, Priority.ALWAYS);
    withChildren(txtFilter, beanTree);
    beanTree.withSelectionChangeListener(this).setContextMenu(new LeftPaneContextMenu(clazz));


    load();
  }

  private void load(){
    uiList.clear();
    for (Bean obj : data.store().createQuery(clazz).find().toList()){
      TreeItem<Bean> item = new TreeItem<>(obj);
      uiList.add(item);
    }
  }

  public Bean getSelection(){
    return beanTree.getSelection();
  }

  public Optional<Bean> getSelectionOptional(){
    return Optional.ofNullable(beanTree.getSelection());
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

  public void reload() {
    load();
  }

  // this is called by the child beanTree
  @Override
  public void selectionChanged(Bean newSelection) {
    if (selectionChangedListener != null){
      selectionChangedListener.selectionChanged(newSelection);
    }
  }

  public FilterableBeansTree withSelectionChangedListener(SelectionChanged<Bean> selectionChangedListener) {
    this.selectionChangedListener = selectionChangedListener;
    return this;
  }

  public BeansTree getBeanTree(){
    return beanTree;
  }

}
