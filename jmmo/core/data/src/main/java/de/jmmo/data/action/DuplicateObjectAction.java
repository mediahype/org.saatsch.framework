package de.jmmo.data.action;

import java.util.List;

import org.joda.beans.Bean;

import de.jmmo.cdi.container.JmmoContext;
import de.jmmo.data.DataSink;
import de.jmmo.data.api.AppIdSuggester;
import de.jmmo.data.api.IntlStringService;
import de.jmmo.data.api.beans.BeanService;
import de.jmmo.data.impl.BeanAndProperty;
import de.jmmo.data.mongo.EditorObject;

/**
 * In order to duplicate an object, it not only has to be cloned, but also 
 * <ul>
 * <li>a new appId has to be assigned as the appId is a unique index in the database.</li>
 * <li>Intl Strings referenced from the Object have to be cloned</li>
 * </ul>
 * 
 * at that moment we only create a clone of the given Object. Later, a dialog could show a selection
 * of referenced objects with the option to clone them aswell.
 * 
 * @author saatsch
 *
 */
public class DuplicateObjectAction implements IAction {

  private Bean original;

  public DuplicateObjectAction(Bean object) {
    this.original = object;
  }

  public Object execute() {
    AppIdSuggester suggester = JmmoContext.getBean(AppIdSuggester.class);
    IntlStringService stringService = JmmoContext.getBean(IntlStringService.class);
    DataSink data = JmmoContext.getBean(DataSink.class);
    BeanService beanService = JmmoContext.getBean(BeanService.class);

    if (original instanceof EditorObject) {

      EditorObject o = (EditorObject) original;
      EditorObject clone = o.clone();
      clone.setName(AppIdSuggester.incrementString(o.getName()));

      // suggest new appId, set it and save the clone.
      String newAppId = suggester.suggest(o.getAppId(), o.getClass());
      clone.setAppId(newAppId);
      data.save(clone);


      // copy intl. Strings from original to clone
      List<BeanAndProperty> intlStrings = beanService.findIntlStrings(original);
      for (BeanAndProperty str : intlStrings) {
        stringService.duplicate(original, str.getProp(), newAppId);
      }


    }
    return null;
  }
}
