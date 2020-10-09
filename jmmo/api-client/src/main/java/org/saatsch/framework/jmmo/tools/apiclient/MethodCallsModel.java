package org.saatsch.framework.jmmo.tools.apiclient;

import java.util.ArrayList;
import java.util.List;

import org.saatsch.framework.jmmo.tools.apiclient.model.MethodCallVO;

public class MethodCallsModel {

  private final List<MethodCallVO> methodCalls = new ArrayList<>();

  
  public List<MethodCallVO> getMethodCalls() {
    return methodCalls;
  }

  public void addMethodCall(Class<?> declaredIn, String methodName) {
      MethodCallVO call = new MethodCallVO();
      call.setClassName(declaredIn.getName());
      call.setMethodName(methodName);
      methodCalls.add(call);
  }
  
}
