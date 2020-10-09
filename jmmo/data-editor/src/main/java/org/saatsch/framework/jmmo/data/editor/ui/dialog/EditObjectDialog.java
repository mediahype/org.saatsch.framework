package org.saatsch.framework.jmmo.data.editor.ui.dialog;

import org.joda.beans.Bean;
import org.joda.beans.Property;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.saatsch.framework.jmmo.data.api.PropertyUtil;
import org.saatsch.framework.jmmo.data.editor.action.ChangeAppIdAction;
import org.saatsch.framework.jmmo.data.editor.ui.tabcontent.EditorTabContent;

/**
 * Context dialog for editing an existing Object.
 * 
 * @author saatsch
 * 
 */
public class EditObjectDialog extends AbstractObjectContextDialog {

  private static final Logger LOG = LoggerFactory.getLogger(EditObjectDialog.class);

  private Bean currentBean;

  private Property<Object> nameProperty;

  private final String oldAppId;


  public EditObjectDialog(EditorTabContent parent) {
    super(parent);
    this.currentBean = parent.getCurrentlySelected();
    nameProperty = PropertyUtil.getGivingNameMainProperty(currentBean);

    oldAppId = (String) PropertyUtil.getAppIdProperty(currentBean).get();

  }

  @Override
  protected String getTitle() {
    return "Edit Object";
  }

  @Override
  protected void okPressed() {

    // set the name
    
    setName(txtName.getText());

    new ChangeAppIdAction(currentBean, txtId.getText(), oldAppId).execute();

    // TODO: check if something was changed and only then repaint.
    parentTab.repaintAll();

    shell.dispose();

  }

  @Override
  protected void postCreateContents() {

    // we do not allow changing an object's type.
    cmbSubClasses.setEnabled(false);

    txtName.setText(getName());
    txtId.setText(PropertyUtil.getAppIdProperty(currentBean).get().toString());


    // select the class in the combo box. Purely for User Information
    int beanClassIndex = cmbSubClasses.indexOf(currentBean.metaBean().beanType().getSimpleName());
    if (beanClassIndex == -1) {
      LOG.info("Class name {} not found", currentBean.metaBean().beanType().getSimpleName());
    } else {
      cmbSubClasses.select(beanClassIndex);
    }


  }

  private String getName() {
    if (PropertyUtil.isIntlString(nameProperty)) {
      return stringService.loadLocalizedText(currentBean, nameProperty);
    } else {
      return nameProperty.get().toString();
    }
  }

  private void setName(String name) {
    if (PropertyUtil.isIntlString(nameProperty)) {
      stringService.saveLocalizedText(currentBean, nameProperty, name);
    } else {
      nameProperty.set(name);
    }
  }

  // FIXME: this is not used but should be.
  private void changeAppId() {
    stringService.changeIntlStringCoordinate(currentBean, txtId.getText());
    PropertyUtil.getAppIdProperty(currentBean).set(txtId.getText());
  }

}
