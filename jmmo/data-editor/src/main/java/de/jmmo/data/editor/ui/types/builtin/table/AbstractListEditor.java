package de.jmmo.data.editor.ui.types.builtin.table;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.joda.beans.Bean;
import org.joda.beans.Property;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.jmmo.data.api.PropertyUtil;
import de.jmmo.data.editor.ui.types.AbstractEditorComposite;

/**
 * an abstract base class for editors that edit a collection. 
 * 
 * 
 * @author saatsch
 *
 */
public abstract class AbstractListEditor extends AbstractEditorComposite {

  private static final Logger LOG = LoggerFactory.getLogger(AbstractListEditor.class);

  protected final Composite cmpForTable;

  /**
   * the table that displays a list of the referenced objects
   */
  protected final Table tblTable;

  /**
   * the type of the entries in the {@link #collectionToEdit}
   */
  protected Class<? extends Bean> genericClass;

  /**
   * the Collection that gets edited by this Editor.
   */
  protected Collection collectionToEdit;

  private TableRowSelected rowSelect;

  private Button btnDown;

  private Button btnUp;

  private Button btnAdd;

  private Button btnRemove;

  @SuppressWarnings("unchecked")
  public AbstractListEditor(Composite parent, Property<Object> property, Bean objectToEdit) {
    super(parent, property, SWT.BORDER_SOLID, objectToEdit);


    if (null != property) {
      genericClass =
          (Class<? extends Bean>) PropertyUtil.firstTypeArgRecurse(property.metaProperty());
      collectionToEdit = (Collection) property.get();
    }


    GridLayout gridLayout = new GridLayout();
    setLayout(gridLayout);


    cmpForTable = new Composite(this, SWT.BORDER);
    GridData gd_cmpForTable = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
    gd_cmpForTable.heightHint = 158;
    cmpForTable.setLayoutData(gd_cmpForTable);
    GridLayout glCmpForTable = new GridLayout(2, false);
    glCmpForTable.verticalSpacing = 2;
    glCmpForTable.horizontalSpacing = 2;
    cmpForTable.setLayout(glCmpForTable);

    tblTable = new Table(cmpForTable, SWT.BORDER | SWT.FULL_SELECTION);
    GridData gdTblTable = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
    gdTblTable.heightHint = 63;
    gdTblTable.widthHint = 366;
    tblTable.setLayoutData(gdTblTable);
    rowSelect = new TableRowSelected(this);
    tblTable.addSelectionListener(rowSelect);
    tblTable.setHeaderVisible(true);
    tblTable.setLinesVisible(true);


    Composite cmpButtonHolder = new Composite(cmpForTable, SWT.NONE);
    cmpButtonHolder.setLayoutData(new GridData(SWT.RIGHT, SWT.FILL, false, false, 1, 1));

    btnAdd = new Button(cmpButtonHolder, SWT.NONE);
    btnAdd.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        btnAddPressed(e);
      }
    });
    btnAdd.setLocation(0, 0);
    btnAdd.setSize(15, 15);

    btnAdd.setText("+");

    btnRemove = new Button(cmpButtonHolder, SWT.NONE);
    btnRemove.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        btnRemovedPressed(e);
      }
    });
    btnRemove.setLocation(0, 15);
    btnRemove.setSize(15, 15);
    btnRemove.setText("-");



    btnUp = new Button(cmpButtonHolder, SWT.NONE);
    btnUp.setEnabled(false);
    btnUp.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        moveUp();
      }
    });
    btnUp.setBounds(0, 30, 15, 15);
    btnUp.setText("u");

    btnDown = new Button(cmpButtonHolder, SWT.NONE);
    btnDown.setEnabled(false);
    btnDown.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        moveDown();
      }
    });
    btnDown.setBounds(0, 45, 15, 15);
    btnDown.setText("d");

    setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

    fillContent();

  }

  private void fillContent() {

    // if the collection is ordered, up and down buttons are relevant
    if (collectionToEdit instanceof List) {
      btnDown.setEnabled(true);
      btnUp.setEnabled(true);
    }

    // in the case of windowBuilder editing, the provided collection may be null
    if (collectionToEdit != null) {
      // the collection could be unmodifiable...
      try {
        collectionToEdit.addAll(Collections.emptyList());
      } catch (UnsupportedOperationException e) {
        btnAdd.setEnabled(false);
        btnRemove.setEnabled(false);

      }
    }

  }

  /**
   * called when a list item was selected (either by the user or programmatically by calling
   * {@link #select(Bean)}) The implementation must update it's editors (if it has
   * any).
   * 
   * @param selectionIndex the zero-relative index of the item which is currently selected in the UI
   *        widget, or -1 if no item is selected.
   * 
   */
  public abstract void updateEditors(int selectionIndex);



  public Table getTblTable() {
    return tblTable;
  }

  /**
   * moves the currently selected element in the list up by one.
   */
  protected void moveUp() {

    // can only move up if index is 1 or higher
    int selectedIndex = getSelectedIndex();
    if (selectedIndex < 1) {
      return;
    }

    if (collectionToEdit instanceof List) {
      Collections.swap((List<?>) collectionToEdit, selectedIndex, selectedIndex - 1);
      saveObject();
    }
    repaintTable();
    tblTable.select(selectedIndex - 1);


  }



  protected void moveDown() {

    int selectedIndex = getSelectedIndex();
    if (selectedIndex >= collectionToEdit.size() - 1 || selectedIndex == -1) {
      return;
    }

    if (collectionToEdit instanceof List) {
      Collections.swap((List<?>) collectionToEdit, selectedIndex, selectedIndex + 1);
      saveObject();
    }
    repaintTable();
    tblTable.select(selectedIndex + 1);


  }

  /**
   * @return the (zero based) index of the currently selected item in the table or -1 if no item is
   *         selected
   */
  protected int getSelectedIndex() {

    if (tblTable.getSelection().length == 0) {
      return -1;
    }
    return tblTable.indexOf(tblTable.getSelection()[0]);
  }



  /**
   * possibility to programmatically select a row in the table
   * 
   * @param toSelect
   */
  protected void select(Bean toSelect) {
    for (TableItem item : tblTable.getItems()) {
      if (item.getData() instanceof Bean) {
        Bean bean = (Bean) item.getData();
        if (bean.equals(toSelect)) {
          tblTable.setSelection(item);
          rowSelect.selected();
          return;
        }
      }
    }
  }


  protected abstract void btnAddPressed(SelectionEvent e);

  protected abstract void btnRemovedPressed(SelectionEvent e);

  /**
   * tells the implementation to repaint the table {@link #tblTable}
   */
  protected abstract void repaintTable();

}
