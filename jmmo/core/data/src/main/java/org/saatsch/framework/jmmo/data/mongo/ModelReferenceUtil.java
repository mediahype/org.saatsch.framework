package org.saatsch.framework.jmmo.data.mongo;

import java.util.HashSet;
import java.util.Set;

import org.joda.beans.Bean;
import org.joda.beans.MetaProperty;
import org.saatsch.framework.jmmo.data.DataSink;
import org.saatsch.framework.jmmo.data.api.BeanReference;
import org.saatsch.framework.jmmo.data.api.PropertyUtil;
import org.saatsch.framework.jmmo.data.api.ReferenceDirection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author saatsch
 *
 */
class ModelReferenceUtil {
  private static final Logger LOG = LoggerFactory.getLogger(ModelReferenceUtil.class);

  private ModelReferenceUtil() {}



  public static Set<BeanReference> resolveIncomingReferences(Set<ModelReference> modelReferences,
      Bean bean, DataSink data) {

    Set<BeanReference> result = new HashSet<>();

    for (ModelReference ref : modelReferences) {

      data.applyFilterCondition(ModelReferenceUtil.createFilterCondition(ref, bean))
          .forEach(b -> result.add(new BeanReference(b, ReferenceDirection.INCOMING)));



    }

    return result;
  }



  static ReferenceFilterCondition createFilterCondition(ModelReference ref, Bean target) {

    if (!ref.getFromClass().equals(target.getClass())) {
      // it is an incoming reference
      MetaProperty<?> targetAppIdProperty = PropertyUtil.getAppIdProperty(ref.getToClass());
      // appId property is always a String
      String value = (String) targetAppIdProperty.get(target);
      return new ReferenceFilterCondition(ref.getFromClass(), createIncomingFilterCondition(ref),
          value);
    } else {
      // it is an outgoing reference
      return null;
    }



  }


  private static String createIncomingFilterCondition(ModelReference ref) {

    return String.join(".", ref.getFrom().getProperties()) + "."
        + PropertyUtil.getAppIdPropertyName((Class<? extends Bean>) ref.getToClass()) + " = ";

  }

}
