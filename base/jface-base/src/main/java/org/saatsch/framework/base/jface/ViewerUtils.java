package org.saatsch.framework.base.jface;

import java.util.Optional;

import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;

public class ViewerUtils {

  public static <T> T getSelected(StructuredViewer viewer, Class<T> expected) {
    return (T) ((StructuredSelection)viewer.getSelection()).getFirstElement();
  }

  public static <T> Optional<T> getSelectedOptional(StructuredViewer viewer, Class<T> expected) {
    return Optional.ofNullable( (T) ((StructuredSelection)viewer.getSelection()).getFirstElement());
  }

  public static Object getSelected(StructuredViewer viewer) {
    return ((StructuredSelection)viewer.getSelection()).getFirstElement();
  }
  
  
}
