package de.osrg.base.beans.change;

/**
 * a change operation can either be SET (if a property needs to be set) or ADD (if the change is an
 * add operation to a list)
 * TODO: REMOVE from List.
 * 
 * @author saatsch
 *
 */
public enum ChangeOp {

  SET, ADD

}
