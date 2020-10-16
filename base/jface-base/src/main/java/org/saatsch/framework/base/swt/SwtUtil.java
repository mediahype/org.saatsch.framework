package org.saatsch.framework.base.swt;

import java.util.Objects;
import java.util.Optional;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

public class SwtUtil {

  private SwtUtil() {}

  public static void addMenuItem(Menu toMenu, String text, SelectionListener listener) {
    MenuItem menuItem = new MenuItem(toMenu, SWT.NONE);
    menuItem.setText(text);
    menuItem.addSelectionListener(listener);
  }

  /**
   * returns an optional of the currently selected {@link TableItem} in the given {@link Table}.
   * 
   * @param table the Table
   * @return an optional of the currently selected {@link TableItem} in the given {@link Table}.
   */
  public static Optional<TableItem> selected(Table table) {

    if (table.getSelection().length == 0) {
      return Optional.empty();
    }

    return Optional.ofNullable(table.getSelection()[0]);

  }

  public static void disposeChildren(Composite cmp) {
    for (Control c : cmp.getChildren()) {
      c.dispose();
    }
  }

  public static void selectInCombo(Combo combo, String string) {
    Objects.requireNonNull(combo);
    Objects.requireNonNull(string);

    for (int i = 0; i < combo.getItems().length; i++) {
      if (string.equals(combo.getItem(i))) {
        combo.select(i);
      }
    }

  }

  private static void resizeColumn(TableColumn tableColumn_) {
    tableColumn_.pack();
  }



  /**
   * resized table columns to fit containing text.
   * 
   * @param table
   */
  public static void pack(Table table) {
    for (TableColumn tc : table.getColumns())
      resizeColumn(tc);
  }

}
