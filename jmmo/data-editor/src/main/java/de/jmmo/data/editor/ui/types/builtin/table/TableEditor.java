package de.jmmo.data.editor.ui.types.builtin.table;

import static de.jmmo.data.api.PropertyUtil.*;

import java.util.Collection;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.joda.beans.Bean;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaBean;
import org.joda.beans.MetaProperty;
import org.joda.beans.Property;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.jmmo.cdi.container.JmmoContext;
import de.jmmo.data.DataSink;
import de.jmmo.data.annotations.JmmoEditorHidden;
import de.jmmo.data.api.PropertyUtil;
import de.jmmo.data.editor.ui.types.EditorCreatorUtil;
import de.jmmo.data.editor.ui.types.Repaintable;

/**
 * edits an embedded List of objects.
 * 
 * @author saatsch
 *
 */
public class TableEditor extends AbstractListEditor implements Repaintable {
  private static final Logger LOG = LoggerFactory.getLogger(TableEditor.class);

  /**
   * the child editors should be painted into this composite
   */
  private final Composite cmpTableWidgetsContent;


  /**
   * constructs a new {@link TableEditor}.
   * 
   * @param parent the parent {@link Composite}
   * @param property the property that contains the data to be edited
   * @param beanToEdit the object that is persited upon changes to the property
   */
  public TableEditor(Composite parent, Property<Object> property, Bean beanToEdit) {
    super(parent, property, beanToEdit);


    cmpTableWidgetsContent = new Composite(cmpForTable, SWT.NONE);
    GridLayout gl_cmpTableWidgetsContent = new GridLayout(1, false);
    gl_cmpTableWidgetsContent.verticalSpacing = 0;
    gl_cmpTableWidgetsContent.marginHeight = 0;
    gl_cmpTableWidgetsContent.marginWidth = 0;
    gl_cmpTableWidgetsContent.horizontalSpacing = 0;
    cmpTableWidgetsContent.setLayout(gl_cmpTableWidgetsContent);

    cmpTableWidgetsContent.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));

    if (null != property){
      addColumns();
      repaintTable();      
    }
    

  }



  private void addColumns() {
    MetaBean metaBean = null;
    try {
      metaBean = MetaBean.of(genericClass);      
    }catch (IllegalArgumentException e) {
      LOG.error("cannot create editor for {}", property.name());
      return;
    }

    for (MetaProperty<?> m : metaBean.metaPropertyIterable()) {
      
      if (!isPropertyAnnotatedWith(m, JmmoEditorHidden.class)) {
        addColumn(m.name(), 200);        
      }
      
    }
  }



  @SuppressWarnings("unchecked")
  @Override
  protected void repaintTable() {

    if (collectionToEdit.isEmpty()) {
      return;
    }

    tblTable.removeAll();
    for (Bean bean : (Collection <? extends Bean>) collectionToEdit) {
      TableItem ti = new TableItem(tblTable, SWT.NONE);
      ti.setData(bean);

      int i = 0;
      for (MetaProperty m : bean.metaBean().metaPropertyIterable()) {
        if (!isPropertyAnnotatedWith(m, JmmoEditorHidden.class)) {
          ti.setText(i, PropertyUtil.getNameOrToString(m.get(bean)));
          i++;          
        }
      }

    }

    for (TableColumn col : tblTable.getColumns()) {
      col.pack();
    }

  }



  private void drawEditors(Bean bean) {

    EditorCreatorUtil.createEditors(cmpTableWidgetsContent, bean, objectToEdit);

    cmpTableWidgetsContent.layout();
    Composite cmp = cmpTableWidgetsContent.getParent();
    while (null != cmp) {
      cmp.layout();
      cmp = cmp.getParent();
    }

  }


  /**
   * adds a column to the table.
   * 
   * @param caption
   * @param width
   */
  protected void addColumn(String caption, int width) {
    TableColumn tblclmn = new TableColumn(tblTable, SWT.NONE);
    tblclmn.setWidth(width);
    tblclmn.setText(caption);
  }

  public Composite getCmpMyContent() {
    return cmpForTable;
  }


  @Override
  protected void btnAddPressed(SelectionEvent e) {

    // create a new instance.
    // TODO: make sure the property represents a Bean

    Bean newInstance = PropertyUtil.newInstance(genericClass);
    PropertyUtil.initPointers(newInstance);
    
    // add it to the list and save the containing Bean
    ((Collection) property.get()).add(newInstance);
    saveObject();
    repaintTable();

    select(newInstance);

  }



  @Override
  protected void btnRemovedPressed(SelectionEvent e) {
    collectionToEdit.remove(tblTable.getSelection()[0].getData());
    JmmoContext.getBean(DataSink.class).save(property.bean());
    repaintTable();
    // TODO: remove obsolete children widgets
  }



  @Override
  public void updateEditors(int selectionIndex) {

    for (Control c : cmpTableWidgetsContent.getChildren()) {
      c.dispose();
    }

    if (cmpTableWidgetsContent.getChildren().length == 0) {
      drawEditors((Bean) tblTable.getSelection()[0].getData());
    }

  }



  @Override
  public void repaint() {
    repaintTable();

  }

}
