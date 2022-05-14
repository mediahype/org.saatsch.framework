package org.saatsch.framework.jmmo.data.editor.ui.composite.filterable.tree;

import java.util.ArrayList;
import java.util.List;

import org.joda.beans.Bean;

import com.mongodb.BasicDBObject;

import org.saatsch.framework.jmmo.cdi.container.JmmoContext;
import org.saatsch.framework.jmmo.data.annotations.JmmoCategories;
import org.saatsch.framework.jmmo.data.DataSink;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dev.morphia.Datastore;
import dev.morphia.query.Query;

public class CategorizingDataProvider {

  private static final Logger LOG = LoggerFactory.getLogger(CategorizingDataProvider.class);

  private String distinct;
  private List<FilterVO> filters;
  private Class<? extends Bean> baseClazz;
  private JmmoCategories annotation;
  private Integer level;
  private String label;

  /**
   * in order to gain speed by loading only a part of each object in a query result, later selecting
   * an object in the editor would have to reload the object - which is not yet implemented.
   */
  private static final boolean FAST_MODE = false;

  /**
   * 
   * The contract is that one of distinct and filters can be <code>null</code> but not both!
   * 
   * @param baseClazz the collection class. Must be annotated with {@link JmmoCategories}.
   * @param distinct the name of the property for which distinct values should be retrieved.
   * @param filters a list of filters.
   * 
   */
  public CategorizingDataProvider(Class<? extends Bean> baseClazz, String distinct,
      List<FilterVO> filters, Integer level) {

    if (distinct == null && filters == null) {
      throw new IllegalArgumentException("distinct and filters must not both be null");
    }

    this.baseClazz = baseClazz;
    this.annotation = baseClazz.getAnnotation(JmmoCategories.class);
    this.distinct = distinct;
    this.filters = filters;
    this.level = level;
  }


  public List getChildren() {

    Datastore store = JmmoContext.getBean(DataSink.class).store();

    // return Beans
    if (distinct == null) {
      long tick = System.currentTimeMillis();
      Query<? extends Bean> query = store.createQuery(baseClazz).disableValidation();
      for (FilterVO filter : filters) {
        query.filter(filter.condition + " = ", filter.value);
      }
      if (FAST_MODE) {
        projectFields(query);
      }
      try {
        return query.asList();
      } finally {
        LOG.debug("children loaded in {}ms.", System.currentTimeMillis() - tick);
      }
    }

    // return Strings
    if (filters == null) {
      return buildFromStringList(store.getCollection(baseClazz).distinct(distinct));
    }


    // return Strings
    BasicDBObject dbObject = new BasicDBObject();
    for (FilterVO filter : filters) {
      dbObject.append(filter.condition, filter.value);
    }
    return buildFromStringList(store.getCollection(baseClazz).distinct(distinct, dbObject));



  }


  private void projectFields(Query<? extends Bean> query) {
    // TODO: project the giving name properties.
    query.project("name", true);
  }


  private List<CategorizingDataProvider> buildFromStringList(List<String> inp) {

    List<CategorizingDataProvider> ret = new ArrayList<>();
    for (String s : inp) {

      String prop = CDPUtil.getCategoryPropertyName(annotation, level + 1);


      List<FilterVO> addFilter = addFilter(s);


      CategorizingDataProvider catDP =
          new CategorizingDataProvider(baseClazz, prop, addFilter, level + 1);
      catDP.setLabel(s.substring(s.lastIndexOf('.') + 1));
      ret.add(catDP);
    }

    return ret;

  }

  private List<FilterVO> addFilter(String s) {

    List<FilterVO> ret;
    if (null == filters) {
      ret = new ArrayList<>();
    } else {
      ret = new ArrayList<>(filters);
    }

    // what is my distinct, is my children's filter !
    ret.add(new FilterVO(distinct, s));
    return ret;
  }


  private void setLabel(String s) {
    this.label = s;

  }


  /**
   * returns the label
   * 
   * @return the label. Can be empty or <code>null</code>
   */
  public String getLabel() {
    return label;
  }



  /**
   * 
   * @author saatsch
   *
   */
  public class FilterVO {

    private final String condition;
    private final String value;

    public FilterVO(String condition, String value) {
      this.condition = condition;
      this.value = value;
    }

    public String getCondition() {
      return condition;
    }

    public String getValue() {
      return value;
    }

  }


}
