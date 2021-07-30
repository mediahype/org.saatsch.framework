package org.saatsch.framework.base.jface.widgets;

import org.eclipse.jface.window.ToolTip;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;

public class HtmlTooltip extends ToolTip {

  private String text;

  public HtmlTooltip(Control control, String text) {
    super(control);
    this.text = text;
  }

  @Override
  protected Composite createToolTipContentArea(Event event, Composite parent) {
    Browser browser = new Browser( parent, SWT.NONE );
    browser.setText(text);
    browser.setBounds(0, 0, 200, 64);
    return browser;
  }

}
