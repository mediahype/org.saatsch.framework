package de.osrg.tools.apiclient;

import java.util.List;

import de.osrg.base.simplescript.Simplescript;
import de.osrg.base.simplescript.swt.ScriptsModel;
import de.osrg.tools.apiclient.model.MethodCallVO;

public class UserDataModel {

  private final ScriptsModel scriptsModel = new ScriptsModel();
  
  private final MethodCallsModel methodCallsModel = new MethodCallsModel();

  public List<Simplescript> getScripts() {
    return scriptsModel.getScripts();
  }

  public void addMethodCall(Class<?> declaredIn, String name) {
    methodCallsModel.addMethodCall(declaredIn, name);
    
  }

  public List<MethodCallVO> getMethodCalls() {
     return methodCallsModel.getMethodCalls();
  }

  public void addScript(String scriptName) {
    scriptsModel.addScript(scriptName);
    
  }

  public ScriptsModel getScriptsModel() {
    return scriptsModel;
    
  }
  
  
  
  
}
