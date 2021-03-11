package org.saatsch.framework.base.expressions;

import java.beans.PropertyChangeListener;

/**
 * instances can be added to instances of {@link Expressions}. Will be notified when
 * {@link Expressions#setVariable(String, Object)} is called.
 * 
 * @author saatsch
 *
 */
public abstract class KeysChangedListener implements PropertyChangeListener {


}
