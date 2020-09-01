package de.jmmo.data.impl;

import java.util.List;

import org.joda.beans.Bean;

import de.jmmo.data.api.Pointer;
import de.jmmo.data.api.beans.BeanService;
import de.jmmo.data.impl.visitor.IntlStringCollector;
import de.jmmo.data.impl.visitor.PointerCollector;

public class BeanServiceImpl implements BeanService {

  /** 
   * {@inheritDoc}
   */
  @Override
  public List<BeanAndProperty> findIntlStrings(Bean bean) {

    IntlStringCollector c = new IntlStringCollector();
    c.visit(bean);
    return c.getResult();
    
  }

  @Override
  public List<Pointer> findPointers(Bean bean) {
    PointerCollector p = new PointerCollector();
    p.visit(bean);
    return p.getResult();
    
  }

//  @Override
//  public Bean translate(Bean bean) {
//
//    BeanTranslator t = new BeanTranslator(bean);
//    t.visit(bean);
//    
//    return null;
//  }

}
