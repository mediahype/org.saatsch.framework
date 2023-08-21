package org.saatsch.framework.base.jfxbase.demo;

import javafx.application.Application;

import org.junit.Test;
import org.saatsch.framework.base.jfxbase.control.VBox;
import org.saatsch.framework.base.jfxbase.widget.Loading;

public class LoadingDemo extends AbstractDemo{

  @Override
  protected void fill(VBox root) {

    Loading x = new Loading().withTask( () ->  {

    	for (int i= 0 ; i<1000000; i++) {

    	}

	} );

    x.show();
    x.run();

    // this does not get called.
    x.close();

  }

  
  @Test
  public void test() {
    Application.launch(this.getClass());
  }
  
}
