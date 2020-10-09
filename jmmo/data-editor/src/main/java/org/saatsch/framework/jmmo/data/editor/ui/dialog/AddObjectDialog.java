package org.saatsch.framework.jmmo.data.editor.ui.dialog;


import org.apache.commons.lang3.StringUtils;

import org.saatsch.framework.jmmo.data.DupKeyException;
import org.saatsch.framework.jmmo.data.action.CreateObjectAction;
import org.saatsch.framework.jmmo.data.editor.DataEditorException;
import org.saatsch.framework.jmmo.data.editor.ui.tabcontent.EditorTabContent;
import org.saatsch.framework.base.swt.MessageBoxUtil;

/**
 * Dialog for creating a new Object.
 * 
 * @author saatsch
 * 
 */
public class AddObjectDialog extends AbstractObjectContextDialog {

  public AddObjectDialog(EditorTabContent parent) {
    super(parent);

  }



  /**
   * ok was pressed. User wants to create object.
   */
  @Override
  protected void okPressed() {
    if (StringUtils.isEmpty(txtName.getText())) {
      MessageBoxUtil.showErrorMessage("Please enter a Name.", shell);
    }

    CreateObjectAction create = new CreateObjectAction(txtName.getText(), txtId.getText(),
        cmbSubClasses.getText(), objectClass);

    try {
      ret = create.execute();
    } catch (DupKeyException dke) {
      throw new DataEditorException(
          "The Object ID is already taken. You can try a different one or press the \"Suggest\" button.",
          dke);
    }

    shell.dispose();

  }



  @Override
  protected String getTitle() {
    return "Add Object";
  }



  @Override
  protected void postCreateContents() {

    String newName = objectClass.getSimpleName() + " " + "unknown";
    txtName.setText(newName);
    txtName.selectAll();

    String suggest = suggester.suggest(newName, objectClass);
    txtId.setText(suggest);


  }
}
