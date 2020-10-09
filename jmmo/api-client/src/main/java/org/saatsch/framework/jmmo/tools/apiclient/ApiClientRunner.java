package org.saatsch.framework.jmmo.tools.apiclient;

import org.saatsch.framework.jmmo.basegame.client.BeanNamespaceImpl;
import org.saatsch.framework.jmmo.cdi.container.JmmoContext;
import org.saatsch.framework.base.beans.change.BeanNamespace;
import org.saatsch.framework.base.simplescript.StepTypeRegistry;
import org.saatsch.framework.jmmo.tools.apiclient.model.AssertVO;
import org.saatsch.framework.jmmo.tools.apiclient.model.MethodCallVO;
import org.saatsch.framework.jmmo.tools.apiclient.model.WaitVO;
import org.saatsch.framework.jmmo.tools.apiclient.ui.ApiClientMainShell;

public class ApiClientRunner {

  public static void main(String[] args) {

    JmmoContext.alias(BeanNamespace.class, BeanNamespaceImpl.class);


    StepTypeRegistry.addType(AssertVO.class);
    StepTypeRegistry.addType(MethodCallVO.class);
    StepTypeRegistry.addType(WaitVO.class);

    try {
      ApiClientMainShell window = new ApiClientMainShell();
      window.open();
    } finally {

    }

    ApiClientUserData.saveUserData();

  }


}
