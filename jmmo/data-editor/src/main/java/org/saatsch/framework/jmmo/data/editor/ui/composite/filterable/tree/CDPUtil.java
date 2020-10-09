package org.saatsch.framework.jmmo.data.editor.ui.composite.filterable.tree;

import java.util.Arrays;
import java.util.List;

import org.saatsch.framework.jmmo.data.annotations.Category;
import org.saatsch.framework.jmmo.data.annotations.CategoryType;
import org.saatsch.framework.jmmo.data.annotations.JmmoCategories;

public class CDPUtil {

  
  public static String getCategoryPropertyName(JmmoCategories cats, int level) {
    
    
    List<Category> list = Arrays.asList(cats.value());
    
    if (level == list.size()) {
      return null;
    }
    
    Category category = list.get(level);
    
    if (category.type() == CategoryType.CLASS) {
      return "className";
    }
    
    return category.propertyName();
    
    
  }
  
}
