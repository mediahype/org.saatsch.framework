package org.saatsch.framework.base.expressions;

import static java.util.Objects.*;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import java.util.concurrent.ConcurrentHashMap;
import javax.el.ELException;
import javax.el.ExpressionFactory;
import javax.el.PropertyNotFoundException;
import javax.el.ValueExpression;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.odysseus.el.ExpressionFactoryImpl;
import de.odysseus.el.tree.TreeBuilderException;
import de.odysseus.el.util.SimpleContext;

/**
 * 
 * small wrapper around the JUEL expression language. Other than the Original EL, this one remembers which keys are defined.
 * 
 * @author saatsch
 *
 */
public class Expressions {

  private static final Logger LOG = LoggerFactory.getLogger(Expressions.class);
  private static final String BOGUS = "bogus";

  protected ExpressionFactory factory = new ExpressionFactoryImpl();
  protected SimpleContext context;

  /**
   * Stores the expression Language keys. I did not find a List implementation that works in all use cases.
   */
  private final Map<String,String> keys = new ConcurrentHashMap<>();

  // private final Set<String> keys = new HashSet<>();
  
  /**
   * The property change support field.
   */
  private final transient PropertyChangeSupport keysChangedListeners = new PropertyChangeSupport(this);

  
  /**
   * 
   * 
   */
  public Expressions(){
    context = new SimpleContext();
  }
  
  /**
   * sets a given variable name (key) to a given Object. Overwrites previous mapping. Informs registered Listeners. 
   * 
   * @param key the key 
   * @param value the value
   */
  public void setVariable(String key, Object value) { 
    context.setVariable(key, factory.createValueExpression(value, value.getClass()));
    // TODO: maybe we should give a copy of the old collection to informListeners?
    Set<String> old = Collections.unmodifiableSet(keys.keySet());
    keys.put(key, BOGUS);
    informListeners();
  }


  public void setFunction(String prefix, String localName, Method method){
      context.setFunction(prefix, localName, method);
  }

  /**
   * evaluates the given expression. Delegates to {@link ValueExpression#getValue(javax.el.ELContext) getValue}.
   * 
   * @param expression the expression
   * @return the outcome of the evaluation as String
   * @throws ELException if the expression could not be evaluated.
   */
  public String evaluate(String expression){
    ValueExpression e = factory.createValueExpression(context, expression, String.class);
    return (String) e.getValue(context);
  }

  /**
   * evaluates the given expression. Delegates to {@link ValueExpression#getValue(javax.el.ELContext) getValue}.
   * 
   * @param expression the expression
   * @return the Object to which the expression evaluated.
   * @throws ELException if the expression could not be evaluated.
   */
  public Object evaluateToObject(String expression){
    ValueExpression e = factory.createValueExpression(context, expression, Object.class);
    return e.getValue(context);
  }
  
  
  
  /**
   * @return a copy of the current Set of keys.
   */
  public Set<String> getKeys(){
    return new HashSet<>(keys.keySet());
  }
  
  public void addKeysChangedListener(KeysChangedListener listener) {
    for (PropertyChangeListener pcl : keysChangedListeners.getPropertyChangeListeners()) {
      if (pcl == listener) {
        return;
      }
    }
    this.keysChangedListeners.addPropertyChangeListener(listener);
  }
  
  /**
   * @param expression the expression, not null
   * @param expected the expected result of the expression, not null
   * @return true, if the expected result was equal to the expected
   * @throws ExpressionsException when the expression could not be evaluated.
   */
  public boolean doAssert(String expression, String expected) throws ExpressionsException {

    requireNonNull(expression);
    requireNonNull(expected);

    ValueExpression e = null;
    try {
      e = factory.createValueExpression(context, expression, String.class);
    } catch (TreeBuilderException tbe) {
      throw new ExpressionsException(tbe.getMessage(), tbe);
    }

    String result = null;

    try {
      result = (String) e.getValue(context);
    } catch (PropertyNotFoundException pnfe) {
      throw new ExpressionsException(pnfe.getMessage(), pnfe);
    }

    return expected.equals(result);
  }
  
  private void informListeners() {
    keysChangedListeners.firePropertyChange("keys", null, Collections.unmodifiableSet(keys.keySet()));
  }
  
  /**
   * resets the expression language context. Does not reset
   */
  public void reset() {
    context = new SimpleContext();
    keys.clear();
    informListeners();
  }
  
}
