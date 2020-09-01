package de.jmmo.data.editor.ui.tabcontent.inspect;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.joda.beans.Bean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.jmmo.cdi.container.JmmoContext;
import de.jmmo.data.mongo.ModelService;

/**
 * content provider for the inspect UI.
 * 
 * @author saatsch
 *
 */
public class InspectContentProvider implements ITreeContentProvider {
  
  private static final Logger LOG = LoggerFactory.getLogger(InspectContentProvider.class);
  private static final ModelService modelService = JmmoContext.getBean(ModelService.class);


  private Object[] elements;

  public InspectContentProvider(Bean bean) {
    this.elements = modelService.resolveReferences(bean).toArray();
  }

  @Override
  public Object[] getElements(Object inputElement) {

    return elements;
 
  }

  @Override
  public Object[] getChildren(Object parentElement) {
    return null;
  }

  @Override
  public Object getParent(Object element) {
    return null;
  }

  @Override
  public boolean hasChildren(Object element) {
    return false;
  }

}
