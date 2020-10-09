package org.saatsch.framework.base.swt.support;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TreeEditor;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

/**
 * can be used to rename an entry in a {@link Tree} inplace. The renaming operation is not vetoable.
 * 
 * @author saatsch
 * 
 */
public abstract class AbstractTreeRenamer extends SelectionAdapter {

  private final Color black = Display.getDefault().getSystemColor(SWT.COLOR_BLACK);

  private final TreeItem[] lastItem = new TreeItem[1];

  private TreeEditor editor;

  @Override
  public void widgetSelected(SelectionEvent event) {

    if (editor == null) {
      editor = new TreeEditor(getTree());
    }

    final TreeItem item = (TreeItem) event.item;
    if (item != null && item == lastItem[0]) {

      final Composite composite = new Composite(getTree(), SWT.NONE);
      composite.setBackground(black);
      final Text text = new Text(composite, SWT.NONE);
      final int inset = 1;
      composite.addListener(SWT.Resize, new Listener() {
        public void handleEvent(Event e) {
          Rectangle rect = composite.getClientArea();
          text.setBounds(rect.x + inset, rect.y + inset, rect.width - inset * 2,
              rect.height - inset * 2);
        }
      });
      Listener textListener = new Listener() {
        public void handleEvent(final Event e) {
          switch (e.type) {
            case SWT.FocusOut:
              item.setText(text.getText());
              renamed(item, text);
              composite.dispose();
              break;
            case SWT.Verify:
              String newText = text.getText();
              String leftText = newText.substring(0, e.start);
              String rightText = newText.substring(e.end, newText.length());
              GC gc = new GC(text);
              Point size = gc.textExtent(leftText + e.text + rightText);
              gc.dispose();
              size = text.computeSize(size.x, SWT.DEFAULT);
              editor.horizontalAlignment = SWT.LEFT;
              Rectangle itemRect = item.getBounds(), rect = getTree().getClientArea();
              editor.minimumWidth = Math.max(size.x, itemRect.width) + inset * 2;
              int left = itemRect.x, right = rect.x + rect.width;
              editor.minimumWidth = Math.min(editor.minimumWidth, right - left);
              editor.minimumHeight = size.y + inset * 2;
              editor.layout();
              break;
            case SWT.Traverse:
              switch (e.detail) {
                case SWT.TRAVERSE_RETURN:
                  item.setText(text.getText());
                  renamed(item, text);
                  composite.dispose();
                  e.doit = false;
                  break;
                case SWT.TRAVERSE_ESCAPE:
                  composite.dispose();
                  e.doit = false;
                  break;
                default:
                  break;
              }
              break;
            default:
              break;
          }
        }
      };
      text.addListener(SWT.FocusOut, textListener);
      text.addListener(SWT.Traverse, textListener);
      text.addListener(SWT.Verify, textListener);
      editor.setEditor(composite, item);
      text.setText(item.getText());
      text.selectAll();
      text.setFocus();
    }
    lastItem[0] = item;

  }

  /**
   * @return the Tree in which an entry should be renamed.
   */
  public abstract Tree getTree();

  /**
   * will be called after the user has entered the new name. At this point in time, the text of the
   * {@link TreeItem} is already set to the new name. In this method the application can update it's
   * model.
   * 
   * @param item
   *        the {@link TreeItem} that was renamed.
   * @param text
   *        the {@link Text} widget containing the new text.
   */
  public abstract void renamed(TreeItem item, Text text);

}
