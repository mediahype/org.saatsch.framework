package org.saatsch.framework.jmmo.data.editor.fx;

import io.vavr.control.Try;
import javafx.application.Application;
import org.junit.Rule;
import org.junit.Test;
import org.saatsch.framework.jmmo.data.action.CreateObjectAction;
import org.saatsch.framework.jmmo.data.editor.fx.model.UnitBase;
import org.saatsch.framework.jmmo.data.mongo.MorphiaMongoDataSink;

public class Launch {

  @Rule
  public LocalRule ctx = new LocalRule();

  @Test
  public void test(){

    Try.of( ()-> new CreateObjectAction("Unit1", UnitBase.class).execute() );


    MorphiaMongoDataSink dataSink = ctx.getBean(MorphiaMongoDataSink.class);
    System.out.println(dataSink.getAll(UnitBase.class).size());

    Application.launch(DataEditorFxApp.class);

  }

}
