package org.saatsch.framework.jmmo.tools.apiclient.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class ApisContentProvider implements ITreeContentProvider{

  public void dispose() {
    
  }

  public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
  }

  public Object[] getElements(Object inputElement) {
    if (null == getChildren(inputElement)){
      return new Object[0];
    }
    return getChildren(inputElement);
  }

  public Object[] getChildren(Object parentElement) {

    if (parentElement instanceof Roots){
      return ((Roots) parentElement).getChildren().toArray();
    }
    
    if (parentElement instanceof Folder){
      return ((Folder)parentElement).getTasks().toArray();
    }
    
    return new Object[0];
  }

  public Object getParent(Object element) {
    return null;
  }

  public boolean hasChildren(Object element) {
    if (element instanceof AbstractMethod){
      return false;
    }
    return true;
  }
  
  
  public static Roots createModel(List<AbstractMethod> payload){
    
    Map<String,Folder> temp = new HashMap<>();
    List<Folder> ret = new ArrayList<>();
    
    for (AbstractMethod at : payload){
      
      String foldername = at.getDomainName() ;
      if (!temp.containsKey(foldername)){
        temp.put(at.getDomainName(), new Folder(at.getDomainName()));        
      }
      
    }
    
    for (AbstractMethod at : payload){
      
      String foldername = at.getDomainName();
      temp.get(foldername).addMethod(at);
      
    }
    
    
    for (String key : temp.keySet())
    {
      ret.add(temp.get(key));
    }

    
    return new Roots(ret); 
    
  }

}
