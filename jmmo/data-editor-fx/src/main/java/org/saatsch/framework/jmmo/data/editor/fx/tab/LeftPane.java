package org.saatsch.framework.jmmo.data.editor.fx.tab;

import dev.morphia.Datastore;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.joda.beans.Bean;
import org.saatsch.framework.jmmo.cdi.container.JmmoContext;
import org.saatsch.framework.jmmo.data.editor.fx.base.SelectionChanged;
import org.saatsch.framework.jmmo.data.editor.fx.beantree.BeanTree;
import org.saatsch.framework.jmmo.data.editor.fx.beantree.BeanTreeFactory;
import org.saatsch.framework.jmmo.data.mongo.MorphiaMongoDataSink;

public class LeftPane extends VBox {

  private final EditorTab parent;
  private final BeanTree beanTree;


  public LeftPane(EditorTab parent){

    this.parent = parent;

    beanTree = BeanTreeFactory.create(parent.getObjectClass());

    VBox.setVgrow(beanTree, Priority.ALWAYS);

    getChildren().add(new TextField());
    getChildren().add(beanTree);



  }


  public void setSelectionChangedListener(SelectionChanged<Bean> listener) {
    beanTree.setSelectionChangedListener(listener);
  }

}
