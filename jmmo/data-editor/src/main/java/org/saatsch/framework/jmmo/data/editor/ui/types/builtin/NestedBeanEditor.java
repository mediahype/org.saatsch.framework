package org.saatsch.framework.jmmo.data.editor.ui.types.builtin;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.joda.beans.Bean;
import org.joda.beans.Property;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.saatsch.framework.jmmo.data.editor.ui.types.AbstractEditorComposite;
import org.saatsch.framework.jmmo.data.editor.ui.types.EditorCreatorUtil;

public class NestedBeanEditor extends AbstractEditorComposite {
  private Composite cmpContent;
  private static final Logger LOG = LoggerFactory.getLogger(NestedBeanEditor.class);

  public NestedBeanEditor(Composite parent, Property<Object> property, Bean objectToEdit) {
    super(parent, property, SWT.NONE, objectToEdit);


    cmpContent = new Composite(this, SWT.BORDER);
    cmpContent.setLayout(new GridLayout(1, false));
    cmpContent.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

    fillContents();

  }

  private void fillContents() {
    Bean nestedBean = (Bean) property.get();

    EditorCreatorUtil.createEditors(cmpContent, nestedBean, objectToEdit);

    cmpContent.layout();
    Composite cmp = cmpContent.getParent();
    while (null != cmp) {
      // LOG.info("Laying out: {} with layout: {}", cmp, cmp.getLayout());
      cmp.layout();
      cmp = cmp.getParent();
    }

    setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

  }

}
