package de.jmmo.data.impl.visitor;

import static de.jmmo.data.api.PropertyUtil.firstTypeArg;
import static de.jmmo.data.api.PropertyUtil.isBean;
import static de.jmmo.data.api.PropertyUtil.isPropertyAnnotatedWith;
import static de.jmmo.data.api.PropertyUtil.isSupportedCollection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.joda.beans.Bean;
import org.joda.beans.Property;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.jmmo.cdi.container.JmmoContext;
import de.jmmo.data.annotations.runtime.DepthDeleteStop;
import de.jmmo.data.mongo.MorphiaMongoDataSink;
import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Reference;

/**
 * The depth deleter tool is given a persistent Bean, visits it's properties and does a depth first
 * delete on referenced persistent Beans. It does also visits persistent Beans that are inside
 * supported collections.
 * 
 * Depth deleting stops at properties that are annotated {@link DepthDeleteStop}.
 * 
 * The behaviour of the tool when given a Bean whose Object graph contains circular dependencies is
 * undefined.
 * 
 * @author saatsch
 *
 */
public class DepthDeleter extends AbstractBeanVisitor {

  private static final Logger LOG = LoggerFactory.getLogger(DepthDeleter.class);

  @Override
  protected boolean concreteVisit(Bean bean) {
    LOG.debug("visiting {}", bean);

    // iterate over properties and recurse into collections of beans.
    for (String propertyName : bean.propertyNames()) {
      Property<Object> property = bean.property(propertyName);
      if (!isPropertyAnnotatedWith(property, DepthDeleteStop.class) && isSupportedCollection(property)
          && Bean.class.isAssignableFrom(firstTypeArg(property.metaProperty()))) {
        for (Object nestedBean : (Collection) property.get()) {
          concreteVisit((Bean) nestedBean);
        }
      }
    }

    // recurse into nested Beans that are not Collections.
    if (hasDepthDeleteReference(bean)) {
      for (Bean referenced : getDepthDeleteReferences(bean)) {
        concreteVisit(referenced);
      }
    }

    // recursing happened. The bean now has no more depth delete references and we can delete it
    // itself.
    if (bean.getClass().isAnnotationPresent(Entity.class)) {
      LOG.debug("deleting {}", bean);
      JmmoContext.getBean(MorphiaMongoDataSink.class).delete(bean);
    }

    return false;

  }


  /**
   * iterates over the properties of the given Bean and checks if it has any properties that must be
   * depth deleted.
   * 
   * @param bean
   * @return
   */
  private boolean hasDepthDeleteReference(Bean bean) {
    boolean hasOutgoingMongoReference = false;

    for (String propertyName : bean.propertyNames()) {
      Property<Object> property = bean.property(propertyName);

      if (isDepthDeleteCandidate(property)) {
        hasOutgoingMongoReference = true;
      }

    }

    return hasOutgoingMongoReference;
  }

  /**
   * returns a list of beans that are nested inside the given bean and have at least one property
   * that is a candidate for depth deletion.
   * 
   * @param bean
   * @return
   */
  private List<Bean> getDepthDeleteReferences(Bean bean) {
    List<Bean> ret = new ArrayList<>();

    for (String propertyName : bean.propertyNames()) {
      Property<Object> property = bean.property(propertyName);

      if (isDepthDeleteCandidate(property) && null != property.get()) {
        ret.add((Bean) property.get());
      }

    }
    return ret;
  }

  private boolean isMongoReferencedBean(Property<Object> property) {
    return isPropertyAnnotatedWith(property, Reference.class) && isBean(property);
  }

  /**
   * a property is a candidate for depth deletion if it is a {@link Bean} and annotated with
   * {@link Reference} and not annotated with {@link DepthDeleteStop}.
   * 
   * @param property
   * @return
   */
  private boolean isDepthDeleteCandidate(Property<Object> property) {
    return isMongoReferencedBean(property)
        && !isPropertyAnnotatedWith(property, DepthDeleteStop.class);
  }

}
