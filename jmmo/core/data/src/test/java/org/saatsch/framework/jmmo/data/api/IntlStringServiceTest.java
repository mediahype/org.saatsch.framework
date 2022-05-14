package org.saatsch.framework.jmmo.data.api;

import org.junit.Rule;
import org.junit.Test;
import org.saatsch.framework.jmmo.data.api.model.IntlString;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class IntlStringServiceTest {

  public static final String TEST_COORDINATE = "testCoordinate";
  public static final String TEST_CONTENT = "TestContent";

  @Rule
  public LocalRule ctx = new LocalRule();



  @Test
  public void test() {
    IntlStringService stringService = ctx.getBean(IntlStringService.class);
    stringService.saveLocalizedText(TEST_COORDINATE, TEST_CONTENT);
    String localizedText = stringService.loadLocalizedText(TEST_COORDINATE);
    assertEquals(TEST_CONTENT, localizedText);


  }

  @Test
  public void test2(){
    IntlStringService stringService = ctx.getBean(IntlStringService.class);
    stringService.saveLocalizedText(TEST_COORDINATE, TEST_CONTENT);
    List<IntlString> search = stringService.search("Coo");
    assertEquals(1, search.size());
  }

}
