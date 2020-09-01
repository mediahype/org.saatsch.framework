package de.osrg.base.swt;

import java.util.Optional;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Table;
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
   * @param table the Table
   * @return an optional of the currently selected {@link TableItem} in the given {@link Table}.
   */
  public static Optional<TableItem> selected(Table table) {
    
    if (table.getSelection().length == 0) {
      return Optional.empty();
    }
    
    return Optional.ofNullable(table.getSelection()[0]);
    
  }

}
