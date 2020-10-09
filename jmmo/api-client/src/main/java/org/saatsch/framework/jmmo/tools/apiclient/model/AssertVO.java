package org.saatsch.framework.jmmo.tools.apiclient.model;

import org.eclipse.swt.widgets.Shell;
import org.saatsch.framework.jmmo.tools.apiclient.ui.scripts.DialogAssert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.saatsch.framework.jmmo.basegame.client.BeanNamespaceImpl;
import org.saatsch.framework.jmmo.cdi.container.JmmoContext;
import org.saatsch.framework.base.expressions.ExpressionsException;
import org.saatsch.framework.base.simplescript.ExecutionException;
import org.saatsch.framework.base.simplescript.JfaceStep;
import org.saatsch.framework.base.simplescript.OpenableEditor;

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
