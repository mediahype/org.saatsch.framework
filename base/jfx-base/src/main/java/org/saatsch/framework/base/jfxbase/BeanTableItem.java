package org.saatsch.framework.base.jfxbase;

import java.util.ArrayList;
import java.util.List;

import org.joda.beans.Bean;
import org.joda.beans.Property;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

public class BeanTableItem extends TreeItem<Object> {

  private boolean isLeaf;
  private boolean isFirstTimeChildren = true;
  private boolean isFirstTimeLeaf = true;

  public BeanTableItem(Object value) {
    super(value);
  }

  @Override
  public ObservableList<TreeItem<Object>> getChildren() {
    if (isFirstTimeChildren) {
      isFirstTimeChildren = false;

      // First getChildren() call, so we actually go off and
      // determine the children.
      super.getChildren().setAll(buildChildren(this));
    }
    return super.getChildren();
  }

  @Override
  public boolean isLeaf() {
    if (isFirstTimeLeaf) {
      isFirstTimeLeaf = false;
      Object itemValue =  getValue();

      isLeaf = true;
      if (itemValue instanceof Bean ) {
        isLeaf = false;
      }
      if (itemValue instanceof Property) {
         Object propertyValue = ((Property) itemValue).get();
         if (propertyValue instanceof Bean) {
           isLeaf = false;
         }
      }
    }

    return isLeaf;
  }

  private ObservableList<TreeItem<Object>> buildChildren(TreeItem<Object> treeItem) {
    Object o = treeItem.getValue();
    Object toBuild = null;
    if (o instanceof Bean) {
      toBuild = o;
    }else {
      if (o instanceof Property ) {
        if (((Property) o).get() instanceof Bean) {
          toBuild = ((Property) o).get();
        }
      }
    }
    
    
    
    if (toBuild != null && toBuild instanceof Bean) {
      
      List<Property> properties = getProperties((Bean) toBuild) ;
      if (properties != null) {
        ObservableList<TreeItem<Object>> children = FXCollections.observableArrayList();

        for (Property childProp : properties) {
          children.add( new BeanTableItem(childProp));
        }

        return children;
      }
    }

    return FXCollections.emptyObservableList();
  }

  private List<Property> getProperties(Bean bean) {
    List<Property> ret = new ArrayList<>();
    bean.propertyNames().forEach(pName -> ret.add(bean.property(pName)));
    return ret;
  }
  
}
