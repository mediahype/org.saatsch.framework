package de.osrg.base.beans.change;

import java.util.List;

import org.joda.beans.Bean;

public class ChangeApplier {

  /**
   * @param change the {@link PropertyChange} to perform
   * @param target the {@link Bean} to perform the change on.
   * @throws ClassCastException when the change cannot be applied to the target.
   * 
   */
  public static void apply(PropertyChange change, Bean target) {

    // traverse the path and find the target bean
    Bean localTarget = target;

    for (int i = 0; i < change.getPath().size() - 1; i++) {
      PropertyPathElement elem = change.getPath().get(i);
      if (elem.getIndexInList() == null) {
        // case: the path element is a nested Bean
        localTarget = (Bean) localTarget.property(elem.getPropName()).get();
      } else {
        // case: the path element is a list
        localTarget = (Bean) ((List) localTarget.property(elem.getPropName()).get())
            .get(elem.getIndexInList());
      }
    }


    if (change.getOperation().equals(ChangeOp.SET)) {
      localTarget.property(lastPathElem(change).getPropName()).set(change.getValue());
    } else {
      ((List)localTarget.property(lastPathElem(change).getPropName()).get()).add(change.getValue());
    }



  }

  private static PropertyPathElement lastPathElem(PropertyChange change) {
    return change.getPath().get(change.getPath().size() - 1);
  }

}
