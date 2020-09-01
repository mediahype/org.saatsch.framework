package de.osrg.base.swt.widgets.logintercept;

import java.io.OutputStream;
import java.io.PrintStream;

public class LogInterceptor extends PrintStream {
  
  private Appending appending;

  public LogInterceptor(OutputStream out, Appending appending) {
    super(out, true);
    this.appending = appending;
  }

  @Override
  public void print(String s) {
    appending.append(s);
    super.print(s);
  }
  
  @Override
  public PrintStream append(char c) {
    appending.append(String.valueOf(c));
    return super.append(c);
  }


}
