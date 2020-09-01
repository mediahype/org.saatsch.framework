/**
 * A small wrapper API around {@link com.thoughtworks.xstream.XStream}. Entry point to the API is
 * the {@link de.osrg.base.serializing.SerializerFactory}.
 * 
 * The serialized/deserialized Objects do not necessarily need to be {@link java.io.Serializable Serializable}.
 * 
 * The {@link de.osrg.base.serializing.XmlSerializer} is targeted for quick saving/loading of config
 * files or to display objects in a log. There is no XML header and no XML namespaces. It can also
 * perform a deep copy of an object.
 * 
 * Note that the {@link de.osrg.base.serializing.JsonSerializer} can only serialize, not
 * deserialize (an {@link java.lang.UnsupportedOperationException} will be thrown if you try to do
 * this).
 * 
 * 
 */
package de.osrg.base.serializing;
