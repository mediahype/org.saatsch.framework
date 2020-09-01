/**
 * API for describing the change of a property on a bean. Supports nested Beans and Beans in
 * Lists. Does not support
 * vetoing a change.
 * 
 * The entry point to this API is {@link de.osrg.base.beans.change.PropertyChange} which is
 * Serializable as the API is meant to aid in synchronizing Beans over a network.
 * 
 */
package de.osrg.base.beans.change;
