package de.osrg.tools.apiclient.model;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.eclipse.swt.widgets.Shell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.jmmo.cdi.container.JmmoContext;
import de.osrg.base.simplescript.ExecutionException;
import de.osrg.base.simplescript.JfaceStep;
import de.osrg.base.simplescript.OpenableEditor;
import de.osrg.tools.apiclient.ui.scripts.DialogCallEdit;


public class MethodCallVO extends JfaceStep {

  private static final Logger LOG = LoggerFactory.getLogger(MethodCallVO.class);

  /**
   * the fully qualified name of the class on which this method call is defined.
   */
  private String className;

  /**
   * the name of the method to be called.
   */
  private String methodName;

  /**
   * the parameters of the call.
   */
  private List<Object> callParameters = new ArrayList<>();

  public MethodCallVO() {
    setName("New Call");
  }


  /**
   * @return the fully qualified name of the class on which this method call is defined.
   */
  public String getClassName() {
    return className;
  }

  public void setClassName(String className) {
    this.className = className;
  }

  public String getMethodName() {
    return methodName;
  }

  public void setMethodName(String methodName) {
    this.methodName = methodName;
  }

  public List<Object> getCallParameters() {
    return callParameters;
  }

  public void setCallParameters(List<Object> callParameters) {
    this.callParameters = callParameters;
  }

  public Method getMethod() {
    try {
      Class<?> c = Class.forName(className);


      for (Method m : c.getMethods()) {
        if (m.getName().equals(methodName)) {
          return m;
        }
      }
    } catch (ClassNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return null;
  }

  /**
   * convenience method that resolves the called class by it's name.
   * 
   * @return
   */
  public Optional<Class> getCalledClass() {

    try {
      Class<?> c = Class.forName(className);
      return Optional.of(c);
    } catch (ClassNotFoundException e) {
    }
    return Optional.empty();
  }

  @Override
  public String toString() {
    return "MethodCallVO{" + "className='" + className + '\'' + ", methodName='" + methodName + '\''
        + ", callParameters=" + callParameters + '}';
  }


  @Override
  public String getMoreInfo() {
    return className.substring(className.lastIndexOf(".") + 1) + "." + methodName + " "
        + callParameters.toString();
  }

  @Override
  public OpenableEditor createEditDialog(Shell parent) {
    return new DialogCallEdit(parent);
  }


  @Override
  public void execute() throws ExecutionException {
    LOG.info("executing method call: {}", getName());

    // resolve the called class
    Optional<Class> calledClass = getCalledClass();
    if (!calledClass.isPresent()) {
      LOG.error("class {} not found.", getClassName());
      return;
    }

    // resolve the bean from the context.
    Object bean = JmmoContext.getBean(calledClass.get());
    if (bean == null) {
      LOG.error("Bean {} not found in JmmoContext.", calledClass.get());
      return;
    }


    try {
      getMethod().invoke(bean, getCallParameters().toArray());
    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
      LOG.error("Error while executing method call...", e);
    }
    // TODO Auto-generated method stub

  }
}
