package de.jmmo.data.api.beans;

import java.util.List;

import org.joda.beans.Bean;

import de.jmmo.data.api.Pointer;
import de.jmmo.data.impl.BeanAndProperty;

public interface BeanService {

  /**
   * Searches for Intl Strings in a Bean. Recurses into nested Beans. Does NOT look into supported
   * Collections.
   * 
   * @param bean
   * @return
   */
  List<BeanAndProperty> findIntlStrings(Bean bean);

  /**
   * Searches for {@link Pointer}s outgoing from a Bean. Recurses into nested Beans. Also finds
   * Pointers inside supported collections.
   * 
   * @param bean the {@link Bean}.
   * @return the Pointers that are outgoing from the given Bean..
   */
  List<Pointer> findPointers(Bean bean);
  
  
}
