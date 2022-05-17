package org.saatsch.framework.jmmo.data.editor.fx.tab;

import javafx.scene.control.ButtonType;
import org.apache.commons.lang3.StringUtils;
import org.joda.beans.Bean;
import org.saatsch.framework.jmmo.data.DupKeyException;
import org.saatsch.framework.jmmo.data.action.CreateObjectAction;
import org.saatsch.framework.jmmo.data.editor.fx.DataEditorException;

public class AddObjectDialog extends AbstractObjectContextDialog {

  protected Object ret;

  public AddObjectDialog(Class<? extends Bean> objectClass) {
    super(objectClass);
    txtName.setText(objectClass.getSimpleName() + " unknown");
    suggest(null);

    setResultConverter(pressed -> {

//      if (StringUtils.isEmpty(txtName.getText())) {
//        MessageBoxUtil.showErrorMessage("Please enter a Name.", shell);
//      }

      if (pressed == ButtonType.OK){
        CreateObjectAction create = new CreateObjectAction(txtName.getText(), txtId.getText(),
            cmbSubClasses.getSelectionModel().getSelectedItem(), objectClass);

        try {
          ret = create.execute();
        } catch (DupKeyException dke) {
          throw new DataEditorException(
              "The Object ID is already taken. You can try a different one or press the \"Suggest\" button.",
              dke);
        }
      }



      return ret;
    });

  }
}
