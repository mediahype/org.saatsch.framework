package de.jmmo.data.editor.ui.composite.filterable.tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.joda.beans.Bean;

import de.jmmo.cdi.container.JmmoContext;
import de.jmmo.data.DataSink;
import de.jmmo.data.annotations.Category;
import de.jmmo.data.annotations.JmmoCategories;
import de.jmmo.data.api.DataConfig;

public class CategorizingDataProviderBuilder {


  private JmmoCategories annotation;

  private Class<? extends Bean> baseClass;



  public CategorizingDataProviderBuilder(Class<? extends Bean> clazz) {

    baseClass = (Class<? extends Bean>) JmmoContext.getBean(DataConfig.class).getManager()
        .getBaseClassOf(clazz);

    annotation = baseClass.getAnnotation(JmmoCategories.class);

  }

  public List build() {

    // fallback if annotation not present
    if (null == annotation) {
      return buildLegacy();
    }
    
    List<Category> categories = Arrays.asList(annotation.value());

    String prop = CDPUtil.getCategoryPropertyName(annotation, 0);
    CategorizingDataProvider p = new CategorizingDataProvider(baseClass, prop, null, 0);
    return p.getChildren();

  }


  /**
   * no categories present. We have a flat List.
   * 
   * @return
   */
  private List buildLegacy() {
    DataSink data = JmmoContext.getBean(DataSink.class);
    if (null != data) {
      return data.getAll(baseClass);
    }
    return Collections.unmodifiableList(new ArrayList<>());
  }

  private void getDistinctClassNames() {

    //

  }
}
