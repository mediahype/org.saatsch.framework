package org.saatsch.framework.jmmo.data.editor.fx.beantree;

import dev.morphia.Datastore;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import org.joda.beans.Bean;
import org.saatsch.framework.jmmo.cdi.container.JmmoContext;
import org.saatsch.framework.jmmo.data.mongo.MorphiaMongoDataSink;

public class BeanTreeFactory {

  private BeanTreeFactory() {
  }

  public static BeanTree create(Class<? extends Bean> clazz){

    BeanTree tree = new BeanTree();


    ObservableList uiList = tree.getRoot().getChildren();


    Datastore store = JmmoContext.getBean(MorphiaMongoDataSink.class).store();

    List<Bean> dbObjects = (List<Bean>) store.createQuery(clazz).find().toList();

    for (Bean obj : dbObjects){
      TreeItem<Bean> item = new TreeItem<>(obj);
      uiList.add(item);
    }

    return tree;

  }

}
