package de.osrg.tools.apiclient.ui.expressions;

import java.beans.PropertyChangeEvent;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;
import org.joda.beans.Property;

import de.jmmo.cdi.container.JmmoContext;
import de.jmmo.data.mongo.MongoDataObject;
import de.osrg.base.expressions.KeysChangedListener;
import de.osrg.base.jface.beantree.namespaced.NamespaceBeanTree;
import de.osrg.base.serializing.AbstractSerializer;
import de.osrg.base.serializing.SerializerFactory;
import de.osrg.tools.apiclient.cdi.BeanNamespaceImpl;

public class ExpressionsUI extends Composite {

  private final Text txtOutput;
  // private final ListViewer listViewer;

  private AbstractSerializer serializer = SerializerFactory.newJsonSerializer()
      .omitField(MongoDataObject.class, "id");

  private Label lblClassName;
  private NamespaceBeanTree tree;

  public ExpressionsUI(Composite parent) {
    super(parent, SWT.NONE);
    setLayout(new FillLayout(SWT.HORIZONTAL));

    SashForm sashForm = new SashForm(this, SWT.VERTICAL);

    Composite cmpExpressions = new Composite(sashForm, SWT.NONE);
    cmpExpressions.setLayout(new GridLayout(1, false));

    tree = new NamespaceBeanTree(cmpExpressions);

    tree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
    // listViewer.setContentProvider(new ExpressionsContentProvider());
    tree.get().addSelectionChangedListener(new SelectionChanged(this));
    // listViewer.addSelectionChangedListener(new SelectionChanged(this));

    lblClassName = new Label(cmpExpressions, SWT.BORDER);
    lblClassName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

    Composite cmpExprDetail = new Composite(sashForm, SWT.NONE);
    cmpExprDetail.setLayout(new FillLayout(SWT.HORIZONTAL));

    txtOutput =
        new Text(cmpExprDetail, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
    txtOutput.setFont(SWTResourceManager.getFont("Courier New", 8, SWT.NORMAL));
    sashForm.setWeights(new int[] {1, 1});

    if (JmmoContext.getBean(BeanNamespaceImpl.class) != null) {
      fillContents();
    }
  }

  private void fillContents() {
    JmmoContext.getBean(BeanNamespaceImpl.class).addKeysChangedListener(new KeysChangedListener() {
      @Override
      public void propertyChange(PropertyChangeEvent evt) {
        Display.getDefault().asyncExec(() -> {

          BeanNamespaceImpl el = JmmoContext.getBean(BeanNamespaceImpl.class);


          LinkedHashMap<String,Object> collect = new LinkedHashMap<>();
          for (String key :  el.getKeys()) {
            collect.put(key, el.evaluateToObject("#{" + key + "}"));
          }
          getTree().setInput(collect);
          updateText();


        });
      }
    });

  }

  TreeViewer getTree() {
    return tree.get();
  }


  public void updateText() {

    Object selected = getSelected();
    
    if (null == selected) {
      return;
    }

    Object toProcess = null;
    
    if (selected instanceof Entry) {
      toProcess = ((Entry) selected).getValue();
    }else {
      toProcess = selected;
    }
    
    String string = serializer.objectToString(toProcess);
    txtOutput.setText(string);
    if (getSelected() == null) {
      lblClassName.setText("");
    } else {
      lblClassName.setText(getSelected().getClass().getName() + " -- Length of Object's JSON:" + string.length());
    }

  }

  private Object getSelected() {
    Object sel = tree.get().getStructuredSelection().getFirstElement();
    Object ret = sel;

    if (sel instanceof Property) {
      ret = ((Property) sel).get();
    }

    return ret;

  }


}
