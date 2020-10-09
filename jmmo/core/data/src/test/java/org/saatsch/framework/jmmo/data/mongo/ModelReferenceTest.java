package org.saatsch.framework.jmmo.data.mongo;

import org.junit.Assert;
import org.junit.Test;

import org.saatsch.framework.jmmo.data.ExampleBean;
import org.saatsch.framework.jmmo.data.ExampleReferencedBean;

public class ModelReferenceTest {



  @Test
  public void test() {

    ModelReference ref = new ModelReference(ExampleReferencedBean.class,
        new PropertyMongoCoordinate("org.saatsch.framework.jmmo.data.ExampleBean:reference"));

    ReferenceFilterCondition filterCondition = ModelReferenceUtil
        .createFilterCondition(ref, new ExampleBean());

    Assert.assertNull(filterCondition);



  }

}
