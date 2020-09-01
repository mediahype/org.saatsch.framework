package de.osrg.base.swt;

import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;

public class DragAndDrop {

  /**
   * @return and array of {@link Transfer}s containing one instance which is the {@link TextTransfer} instance.
   */
  public static Transfer[] textTransfers() {
    return new Transfer[] {TextTransfer.getInstance()};
  }

  public static TextTransfer textTransfer() {
    return TextTransfer.getInstance();
  }
  
}
