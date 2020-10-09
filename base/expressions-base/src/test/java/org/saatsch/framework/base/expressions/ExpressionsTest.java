package org.saatsch.framework.base.expressions;

import static org.junit.Assert.*;

import java.beans.PropertyChangeEvent;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Assert;
import org.junit.Test;

public class ExpressionsTest {

  @Test
  public void test() {

    Expressions e = new Expressions();

    
    e.setVariable("var", new SomeClass("someContent"));
    Assert.assertEquals("someContent", e.evaluate("${var.someString}"));
    Assert.assertEquals(1, e.getKeys().size());

    e.setVariable("var", new SomeClass("someOtherContent"));
    Assert.assertEquals("someOtherContent", e.evaluate("${var.someString}"));
    Assert.assertEquals(1, e.getKeys().size());

    Assert.assertEquals("var", e.getKeys().toArray()[0]);


  }

  @Test
  public void nonStringTest() {
    
    Expressions expressions = new Expressions();
    expressions.setVariable("someInteger", 2);

    assertEquals("2",expressions.evaluate("#{someInteger}"));

  }
  
  @Test
  public void listenerTest() {
    Expressions cut = new Expressions();
    final AtomicInteger counter = new AtomicInteger(0);

    addListener(cut, counter);
    addListener(cut, counter);
    
    cut.setVariable("var", 3);
    
    assertEquals(2, counter.get());
    
  }

  @Test
  public void functionTest(){
    Expressions cut = new Expressions();

    try {
      cut.setFunction("math", "max", Math.class.getMethod("max", int.class, int.class));
    } catch (NoSuchMethodException e) {
      // intentionally left empty
    }
    cut.setVariable("foo", 3);
    cut.setVariable("bar", 5);
    assertEquals(5, cut.evaluateToObject("${math:max(foo,bar)}") );


    // function can be overwritten by a variable.
    cut.setVariable("math", "x");
    System.out.println(cut.evaluate("${math}"));

  }


  private void addListener (Expressions expressions, AtomicInteger counter) {
    expressions.addKeysChangedListener(new KeysChangedListener() {
      @Override
      public void propertyChange(PropertyChangeEvent evt) {
        counter.incrementAndGet();
      }
    });
    
  }

}
