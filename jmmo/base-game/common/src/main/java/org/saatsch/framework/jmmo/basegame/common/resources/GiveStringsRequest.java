package org.saatsch.framework.jmmo.basegame.common.resources;

public class GiveStringsRequest extends GiveResourceRequest {

  private static final long serialVersionUID = -8730783794119390645L;
  private final String language;

  public GiveStringsRequest(String language) {
    this.language = language;
  }

  public String getLanguage() {
    return language;
  }
  
}
