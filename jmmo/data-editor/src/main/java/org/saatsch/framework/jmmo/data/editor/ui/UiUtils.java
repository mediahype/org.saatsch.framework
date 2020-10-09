package org.saatsch.framework.jmmo.data.editor.ui;

import org.apache.commons.lang3.StringUtils;

import org.saatsch.framework.jmmo.data.editor.Constants;

public class UiUtils {

  public static String emptyOrString(String inp) {
    return StringUtils.isEmpty(inp) ? Constants.EMPTY_LABEL : inp;
  }
  
}
