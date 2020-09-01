package de.jmmo.data.editor.ui;

import org.apache.commons.lang3.StringUtils;

import de.jmmo.data.editor.Constants;

public class UiUtils {

  public static String emptyOrString(String inp) {
    return StringUtils.isEmpty(inp) ? Constants.EMPTY_LABEL : inp; 
  }
  
}
