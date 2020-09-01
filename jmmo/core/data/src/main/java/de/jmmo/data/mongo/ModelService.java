package de.jmmo.data.mongo;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.joda.beans.Bean;

import de.jmmo.cdi.container.JmmoContext;
import de.jmmo.data.api.BeanReference;
import de.jmmo.data.api.DataConfig;
import de.jmmo.data.api.Pointer;
import de.jmmo.data.api.PointerUtil;
import de.jmmo.data.api.ReferenceDirection;
import de.jmmo.data.api.References;
import de.jmmo.data.api.beans.BeanService;

public class ModelService {

  private DataConfig config = JmmoContext.getBean(DataConfig.class);

  private MorphiaMongoDataSink data = JmmoContext.getBean(MorphiaMongoDataSink.class);

  private BeanService beanService = JmmoContext.getBean(BeanService.class);

  /**
   * finds and resolves incoming and outgoing references to a bean.
   * 
   * @param bean
   * @return
   */
  public References resolveReferences(Bean bean) {

    Set<BeanReference> refs = new HashSet<>();
    
    refs.addAll(resolveIncomingReferences(bean));
    refs.addAll(resolveOutgoingReferences(bean));

    return new References(bean, refs );

  }

  /**
   * returns the Beans that have outgoing references that are incoming to the given Bean. Useful to
   * answer the question if an Object can be safely deleted.
   * 
   * @param bean
   * @return
   */
  public Set<BeanReference> resolveIncomingReferences(Bean bean) {

    Set<ModelReference> references = new HashSet<>();

    // if the class of the given bean is not a base class, use the base class.
    if (!config.getManager().getBaseClasses().contains(bean.getClass())) {
      references = config.getManager()
          .getIncomingModelReferences(config.getManager().getBaseClassOf(bean.getClass()));
    } else {
      references = config.getManager().getIncomingModelReferences(bean.getClass());
    }

    return ModelReferenceUtil.resolveIncomingReferences(references, bean, data);

  }


  @SuppressWarnings({"unchecked", "rawtypes"})
  private Set<BeanReference> resolveOutgoingReferences(Bean bean) {

    Set<BeanReference> ret = new HashSet<>();

    List<Pointer> pointers = beanService.findPointers(bean);
    for (Pointer p : pointers) {
      PointerUtil.resolveOptional(p)
          .ifPresent(res -> ret.add(new BeanReference((Bean) res, ReferenceDirection.OUTGOING)));
    }

    return ret;
  }



}
