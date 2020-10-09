package org.saatsch.framework.jmmo.data.editor.ui.tabcontent;

/*
 * #%L nof-edit $Id:$ $HeadURL:$ %% Copyright (C) 2013 http://nof.sf.net %% Licensed under the
 * Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License. #L%
 */

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.saatsch.framework.jmmo.data.editor.ui.dialog.AddObjectDialog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Listener that reacts when "Add object..." was clicked. Queries info about the new Object and
 * triggers that the tree be redrawn.
 * 
 * @author saatsch
 * 
 */
public class AddObjectListener extends SelectionAdapter {

  private final Class oc;

  private final EditorTabContent parent;

  private static final Logger LOG = LoggerFactory.getLogger(AddObjectListener.class);

  public AddObjectListener(Class oc, EditorTabContent parent) {
    super();
    this.oc = oc;
    this.parent = parent;
  }

  @Override
  public void widgetSelected(SelectionEvent e) {
    LOG.debug("Add Object selected ...");
    AddObjectDialog dia = new AddObjectDialog(parent);
    Object newObject = dia.open();

    if (null != newObject) {

      parent.fill();
      parent.calcSize();
    }
  }

}
