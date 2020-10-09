package org.saatsch.framework.jmmo.data.api;

import org.joda.beans.Bean;

/**
 * implemented by model {@link Bean} classes that want to display a custom (e.g. more concise than the
 * default) text when displayed in the Property Tree edit mode of the data editor.
 * 
 * @author saatsch
 *
 */
public interface CustomEditorText {

  String getCustomEditorText();



}
