package org.saatsch.framework.base.swt.widgets.logintercept;

import java.io.PrintStream;
import java.lang.reflect.Field;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogInterceptorFactory {

  private static final Logger LOG = LoggerFactory.getLogger(LogInterceptorFactory.class);
  
  /**
   * init the logging. System.out is rerouted to the given {@link Appending}.
   * 
   * @param appending
   */
  public static void initLogging(Appending appending){
    try {
      doInitLogging(appending);
    } catch (NoSuchFieldException | SecurityException | IllegalArgumentException
        | IllegalAccessException  e) {
      LOG.error("Error while starting LOG interceptor: ", e);
    }
  }
  
  private static void doInitLogging(Appending appending) throws NoSuchFieldException, IllegalAccessException{
    
    PrintStream origOut = System.out;
    PrintStream outInterceptor = new LogInterceptor(origOut, appending);


    Class<?> clazz = LOG.getClass();
    if (null != clazz){
      Field field = clazz.getDeclaredField("TARGET_STREAM");
      if (null!=field){
        field.setAccessible(true);
        field.set(null, outInterceptor);              
      }
    }
    
    System.setOut(outInterceptor);
    
  }
  
}
