package org.saatsch.framework.jmmo.data.editor.fx.tab;

import dev.morphia.Datastore;
import dev.morphia.query.FindOptions;
import java.util.List;
import java.util.stream.Collectors;
import javafx.collections.ObservableList;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import org.joda.beans.Bean;
import org.saatsch.framework.jmmo.cdi.container.JmmoContext;
import org.saatsch.framework.jmmo.data.editor.fx.base.BeanTreeCell;
import org.saatsch.framework.jmmo.data.mongo.MorphiaMongoDataSink;

public class LeftPane extends VBox {

  private final TreeItem rootItem = new TreeItem("root");

  private final EditorTab parent;

  private TreeView<Bean> objectsTree = new TreeView<>();


  public LeftPane(EditorTab parent){

    this.parent = parent;

    objectsTree.setShowRoot(false);
    objectsTree.setRoot(rootItem);
    objectsTree.setCellFactory(new Callback<TreeView<Bean>, TreeCell<Bean>>() {
      @Override
      public TreeCell<Bean> call(TreeView<Bean> param) {
        return new BeanTreeCell();
      }
    });


    VBox.setVgrow(objectsTree, Priority.ALWAYS);

    getChildren().add(new TextField());
    getChildren().add(objectsTree);

    loadObjects();


  }

  private void loadObjects() {
    ObservableList uiList = rootItem.getChildren();
    // uiList.clear();


    Datastore store = JmmoContext.getBean(MorphiaMongoDataSink.class).store();

    List<Bean> dbObjects = (List<Bean>) store.createQuery(parent.getObjectClass()).find().toList();

    for (Bean obj : dbObjects){

      TreeItem<Bean> item = new TreeItem<>(obj);

      uiList.add(item);
    }

    // dbObjects.stream().forEach( obj -> uiList.add(new TreeItem<>(obj)) );



  }

}
