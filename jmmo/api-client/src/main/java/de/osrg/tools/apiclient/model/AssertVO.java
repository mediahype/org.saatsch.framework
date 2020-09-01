package de.osrg.tools.apiclient.model;

import org.eclipse.swt.widgets.Shell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.jmmo.cdi.container.JmmoContext;
import de.osrg.base.expressions.ExpressionsException;
import de.osrg.base.simplescript.ExecutionException;
import de.osrg.base.simplescript.JfaceStep;
import de.osrg.base.simplescript.OpenableEditor;
import de.osrg.tools.apiclient.cdi.BeanNamespaceImpl;
import de.osrg.tools.apiclient.ui.scripts.DialogAssert;

public class AssertVO extends JfaceStep {

  private static final Logger LOG = LoggerFactory.getLogger(AssertVO.class);
  
  private String expression;
  
  private String expected;

  public String getExpression() {
    return expression;
  }

  public void setExpression(String expression) {
    this.expression = expression;
  }

  public String getExpected() {
    return expected;
  }

  public void setExpected(String expected) {
    this.expected = expected;
  }

  @Override
  public String toString() {
    return "AssertVO{" +
        "expression='" + expression + '\'' +
        ", expected='" + expected + '\'' +
        '}';
  }

  @Override
  public String getMoreInfo() {
    return expression + " == " + expected;
  }

  @Override
  public OpenableEditor createEditDialog(Shell parent) {
    return new DialogAssert(parent);
  }

  @Override
  public void execute() throws ExecutionException {
    String expected = getExpected();
    String expression = getExpression();

    try {
      boolean doAssert =
          JmmoContext.getBean(BeanNamespaceImpl.class).doAssert(expression, expected);
      if (doAssert) {
        LOG.info("Assert OK: {} is {}" , expression , expected);
      } else {
        throw new ExecutionException("Assert NOT OK: " + expression + " is NOT " + expected);
      }
    } catch (ExpressionsException e) {
      throw new ExecutionException(e.getMessage(), e);
    }
    
    
  }
}
