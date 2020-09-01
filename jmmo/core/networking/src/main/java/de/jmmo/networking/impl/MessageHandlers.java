package de.jmmo.networking.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import de.jmmo.networking.api.ANetworkMessageHandler;
import de.jmmo.networking.api.NetworkMessageHandler;
import de.osrg.base.classpath.ConfiguringReflections;

/**
 * 
 * Manages message handlers and their instances. On startup, searches for handlers (classes
 * annotated with {@link ANetworkMessageHandler}) and then checks if they implement
 * {@link NetworkMessageHandler}. If one of them does not obey this contract, a Runtime Exception is
 * thrown. Instantiates the handlers.
 * 
 * Holds a mapping from messages to handlers. A message can be listened for by multiple handlers.
 * (And also one handler can listen for multiple messages.) Handlers are discovered by their
 * {@link ANetworkMessageHandler} annotation.
 * 
 * 
 * @author saatsch
 * 
 */
public class MessageHandlers extends ConfiguringReflections {

  private static final Logger LOG = LoggerFactory.getLogger(MessageHandlers.class);

  /**
   * maps messages (message classes) to message handlers(a.k.a. listeners)
   */
  private final Multimap<Class<?>, Class<? extends NetworkMessageHandler>> messagesToHandlers =
      ArrayListMultimap.create();


  public MessageHandlers() {
    init();
  }


  private void init() {
    LOG.info("Handlers init ... ");
    seekHandlerClasses();

    for (Class<?> handler : handlerClasses) {

      // find out messages that listener wants to handle
      ANetworkMessageHandler annotation = handler.getAnnotation(ANetworkMessageHandler.class);
      Class<?>[] listenTos = annotation.listenTo();
      for (Class<?> listenTo : listenTos) {
        messagesToHandlers.put(listenTo, (Class<? extends NetworkMessageHandler>) handler);
      }
    }
  }

  /**
   * @return the mappings from messages to listeners. One message can be listened for by multiple
   *         listeners.
   */
  public Multimap<Class<?>, Class<? extends NetworkMessageHandler>> get() {
    return messagesToHandlers;
  }

  public void handleMessage(IoSession connection, Object message) {

    for (Class<? extends NetworkMessageHandler> lc : getHandlersForMessage(message)) {
      NetworkMessageHandler handler = this.getHandlerByClass(lc);
      handler.handleMessage(connection, message);
    }

  }

  /**
   * returns the handler or handlers for a given message.
   * 
   * @param message the message
   * @return a collection of handlers for the message or an empty collection. never
   *         <code>null</code>.
   */
  public Collection<Class<? extends NetworkMessageHandler>> getHandlersForMessage(Object message) {
    return messagesToHandlers.get(message.getClass());
  }

  private Set<Class<?>> handlerClasses;

  private final Map<Class<?>, Object> listenerInstances = new HashMap<Class<?>, Object>();

  private void seekHandlerClasses() {

    // find all listeners
    handlerClasses = read().getTypesAnnotatedWith(ANetworkMessageHandler.class);

    write(handlerClasses);

    LOG.info("found {} handlerClasses.", handlerClasses.size());

    for (Class<?> listener : handlerClasses) {

      // check if listener implements NetworkMessageHandler
      checkHandlerContract(listener);

      Object instance = null;
      try {
        instance = listener.newInstance();
      } catch (InstantiationException | IllegalAccessException e) {
        LOG.error("Error instanciating listener");
      }
      listenerInstances.put(listener, instance);

    }

  }

  public Set<Class<?>> getHandlerClasses() {
    return handlerClasses;
  }

  /**
   * returns the handler instance given it's class
   * 
   * @param type
   * @return
   */
  @SuppressWarnings("unchecked")
  public <T> T getHandlerByClass(Class<T> type) {

    return (T) listenerInstances.get(type);

  }

  /**
   * handlers must implement {@link NetworkMessageHandler}.
   * 
   * @param handler the listener to check
   */
  private void checkHandlerContract(Class<?> handler) {
    Class<?>[] interfaces = handler.getInterfaces();
    if (interfaces.length == 0) {
      failCheckHandlerContract(handler);
    }
    boolean correct = false;
    for (Class<?> iface : interfaces) {
      if (iface == NetworkMessageHandler.class) {
        correct = true;
      }
    }
    if (!correct) {
      failCheckHandlerContract(handler);
    }
  }

  private void failCheckHandlerContract(Class<?> handler) {
    throw new RuntimeException(handler + " must implement " + NetworkMessageHandler.class);
  }

  @Override
  public String getConfigName() {
    return "handlers";
  }

}
