package org.saatsch.framework.jmmo.tools.apiclient.ui;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import org.saatsch.framework.jmmo.tools.apiclient.ui.widgets.ConvertableToFromObject;
import org.saatsch.framework.jmmo.tools.apiclient.ui.widgets.EnumWidget;
import org.saatsch.framework.jmmo.tools.apiclient.ui.widgets.IntegerWidget;
import org.saatsch.framework.jmmo.tools.apiclient.ui.widgets.StringWidget;

/**
 * this UI manages the editing of a given {@link AbstractMethod}'s call parameters (a.k.a. method arguments).
 * 
 * @author saatsch
 *
 */
public class AbstractMethodUI extends Composite {

  /**
   * list of UI elements that allow the user input of the call parameters.
   */
  private List<ConvertableToFromObject> uiInputs = new ArrayList<>();
  
  /**
   * the method that is being edited.
   */
  private Method method;
  
  /**
   * this constructor creates an empty UI.
   * 
   * @param parent
   * @param style
   */
  public AbstractMethodUI(Composite parent, int style) {
    super(parent, style);
    setLayout(new GridLayout(2, false));
    createHeading("");
  }
  
  
  
  /**
   * this constructor creates the UI for editing the call parameters of the given method.
   * 
   * @param parent
   * @param style
   * @param method
   */
  public AbstractMethodUI(Composite parent, int style, Method method) {
    super(parent, style);
    setLayout(new GridLayout(2, false));
    this.method = method;
    createHeading(method.getName());
    Arrays.asList(method.getParameters()).stream().forEach(m -> createInput(this, m));
    
    
  }

  public void setMethod(Method method) {
    // TODO: delete current content, if present.
    this.method = method;
    createHeading(method.getName());
    Arrays.asList(method.getParameters()).stream().forEach(m -> createInput(this, m));
  }
  
  private void createHeading(String methodName) {
    Label lblNewLabel = new Label(this, SWT.NONE);
    lblNewLabel.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
    lblNewLabel.setText(methodName);
    
  }
  
  /**
   * creates a user input widget 
   * 
   * @param parent
   * @param param
   */
  private void createInput(Composite parent, Parameter param) {
    Label lblArgName = new Label(parent, SWT.NONE);
    if(param.isNamePresent()) {
      lblArgName.setText(param.getName());
    }else {
      lblArgName.setText(param.getType().getSimpleName());      
    }
    
    if (param.getType() == String.class) {
      createText(parent);
    }else if (param.getType().isEnum()) {
      createCombo(parent, (Class<? extends Enum<?>>) param.getType());
    } else if (param.getType() == Integer.class) {
      createInteger(parent);
    }
    else {
      // unhandled type
      createUnhandled(parent);
    }
  
  }
  



  private void createInteger(Composite parent) {
    IntegerWidget number = new IntegerWidget(parent, SWT.BORDER);
    number.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
    uiInputs.add(number);    
  }



  private void createUnhandled(Composite parent) {
    StringWidget text = new StringWidget(parent, SWT.BORDER);
    text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
    text.setEnabled(false);
    uiInputs.add(text);    
    
  }

  
  private void createText(Composite parent) {
    StringWidget text = new StringWidget(parent, SWT.BORDER);
    text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
    uiInputs.add(text);    
  }

  private void createCombo(Composite parent, Class<? extends Enum<?>> e) {
    EnumWidget widget = new EnumWidget(parent, e, SWT.BORDER);
    widget.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
    uiInputs.add(widget);
  }

  
  public List<Object> getArgs(){
    List<Object> collect = uiInputs.stream().map(ConvertableToFromObject::asObject).collect(Collectors.toList());
    return collect;
  }
  
  /**
   * sets the arguments to be edited.
   * 
   * @param args
   */
  public void setArgs(List<Object> args) {
    
    if (uiInputs.size() != args.size()) {
      // cannot load values.
      return;
    }
    
    for (int i = 0; i<uiInputs.size() ; i++) {
      // TODO: skip unhandled
      uiInputs.get(i).fromObject(args.get(i));
    }
    
    
  }
  
}
