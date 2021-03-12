package org.saatsch.framework.jmmo.data.editor.fx.tab;

import dev.morphia.Datastore;
import java.util.List;
import java.util.Optional;
import javafx.collections.ObservableList;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.joda.beans.Bean;
import org.saatsch.framework.jmmo.cdi.container.JmmoContext;
import org.saatsch.framework.jmmo.data.editor.fx.base.SelectionChanged;
import org.saatsch.framework.jmmo.data.editor.fx.beantree.BeanTree;
import org.saatsch.framework.jmmo.data.mongo.MorphiaMongoDataSink;

public class LeftPane extends VBox {

  private final EditorTab parent;

  private BeanTree beanTree = new BeanTree();


  public LeftPane(EditorTab parent){

    this.parent = parent;

    VBox.setVgrow(beanTree, Priority.ALWAYS);

    getChildren().add(new TextField());
    getChildren().add(beanTree);

    loadObjects();

  }

  private void loadObjects() {
    ObservableList uiList = beanTree.getRoot().getChildren();
    // uiList.clear();

    Datastore store = JmmoContext.getBean(MorphiaMongoDataSink.class).store();

    List<Bean> dbObjects = (List<Bean>) store.createQuery(parent.getObjectClass()).find().toList();

    for (Bean obj : dbObjects){
      TreeItem<Bean> item = new TreeItem<>(obj);
      uiList.add(item);
    }
    // dbObjects.stream().forEach( obj -> uiList.add(new TreeItem<>(obj)) );
  }

  public void setSelectionChangedListener(SelectionChanged<Bean> listener) {
    beanTree.setSelectionChangedListener(listener);
  }

}
