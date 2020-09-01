package de.osrg.tools.apiclient;

import de.osrg.base.simplescript.StepTypeRegistry;
import de.osrg.tools.apiclient.model.AssertVO;
import de.osrg.tools.apiclient.model.MethodCallVO;
import de.osrg.tools.apiclient.model.WaitVO;
import de.osrg.tools.apiclient.ui.ApiClientMainShell;

public class ApiClientRunner {

  public static void main(String[] args) {
    
    

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
