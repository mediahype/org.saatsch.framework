package de.jmmo.data.mongo;

import org.junit.Assert;
import org.junit.Test;

import de.jmmo.data.ExampleBean;
import de.jmmo.data.ExampleReferencedBean;

public class ModelReferenceTest {



  @Test
  public void test() {

    ModelReference ref = new ModelReference(ExampleReferencedBean.class,
        new PropertyMongoCoordinate("de.jmmo.data.ExampleBean:reference"));

    ReferenceFilterCondition filterCondition = ModelReferenceUtil
        .createFilterCondition(ref, new ExampleBean());

    Assert.assertNull(filterCondition);



  }

}
