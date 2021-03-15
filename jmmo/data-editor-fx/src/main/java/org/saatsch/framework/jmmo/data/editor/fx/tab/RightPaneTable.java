package org.saatsch.framework.jmmo.data.editor.fx.tab;

import org.joda.beans.Bean;
import org.saatsch.framework.jmmo.data.editor.fx.beantable.BeanTable;

public class RightPaneTable extends AbstractRightPane {

  private final BeanTable table;

  public RightPaneTable() {
    super();

    table = new BeanTable();
    setContent(table);



  }



  @Override
  public void selectionChanged(Bean newSelection) {
    super.selectionChanged(newSelection);
    clear();
    fill(newSelection);
  }

  private void fill(Bean newSelection) {
    table.setBean(newSelection);
  }

  private void clear() {
  }
}
