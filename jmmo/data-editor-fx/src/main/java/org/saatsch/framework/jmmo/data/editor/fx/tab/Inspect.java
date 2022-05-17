package org.saatsch.framework.jmmo.data.editor.fx.tab;

import javafx.collections.FXCollections;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import org.joda.beans.Bean;
import org.saatsch.framework.jmmo.cdi.container.JmmoContext;
import org.saatsch.framework.jmmo.data.api.BeanReference;
import org.saatsch.framework.jmmo.data.api.PropertyUtil;
import org.saatsch.framework.jmmo.data.api.References;
import org.saatsch.framework.jmmo.data.editor.fx.Images;
import org.saatsch.framework.jmmo.data.editor.fx.Img;
import org.saatsch.framework.jmmo.data.mongo.ModelService;

public class Inspect extends ListView<BeanReference> {

  private static final ModelService modelService = JmmoContext.getBean(ModelService.class);

  public Inspect() {
    // default is not visible
    setVisible(false);

    setCellFactory(new Callback<ListView<BeanReference>, ListCell<BeanReference>>() {
      @Override
      public ListCell<BeanReference> call(ListView<BeanReference> param) {
        return new ListCell<>(){
          @Override
          protected void updateItem(BeanReference item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null){
              setText(null);
              setGraphic(null);
            }
            else{
              setText(PropertyUtil.getFullName(item.getBean()));
              setGraphic(selectImage(item));
            }
          }
        };
      }
    });

  }

  private ImageView selectImage(BeanReference item) {
    switch (item.getDirection()) {
      case INCOMING:
        return Images.get(Img.INCOMING_REF);
      case OUTGOING:
        return Images.get(Img.OUTGOING_REF);
      default:
        return null;
    }
  }

  public void inspect(Bean bean) {
    References references = modelService.resolveReferences(bean);
    setItems(FXCollections.observableArrayList(references.get()));
  }
}
