package de.jmmo.basegame.client.handlers;

import java.beans.PropertyChangeSupport;
import java.lang.reflect.Field;

import org.apache.mina.core.session.IoSession;
import org.joda.beans.Bean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.jmmo.basegame.client.Eventing;
import de.jmmo.cdi.container.JmmoContext;
import de.jmmo.networking.api.ANetworkMessageHandler;
import de.jmmo.networking.api.NetworkMessageHandler;
import de.jmmo.networking.beansync.PutIntoNamespace;
import de.osrg.base.beans.change.BeanNamespace;

@ANetworkMessageHandler(listenTo = {PutIntoNamespace.class})
public class PutIntoNamespaceHandler implements NetworkMessageHandler {

  private static final Logger LOG = LoggerFactory.getLogger(PutIntoNamespaceHandler.class);
  
  @Override
  public void handleMessage(IoSession connection, Object message) {
    String beanName = ((PutIntoNamespace) message).getName();
    Bean bean = ((PutIntoNamespace) message).getBeanToPut();
    
    LOG.info("Putting under Name:{} bean:{}", beanName , bean);
    
    // if the Bean has propertyChangeSupport, we must initialize it
    initPropertyChangeSupport(bean);

    JmmoContext.getBean(BeanNamespace.class).putBean(beanName, bean);

    // The bean that is put into the namespace is also posted to Eventing.
    JmmoContext.getBean(Eventing.class).post(bean);
    
  }

  private static void initPropertyChangeSupport(Bean bean) {

    try {
      Field field = bean.getClass().getDeclaredField("propertyChangeSupport");
      field.setAccessible(true);
      field.set(bean, new PropertyChangeSupport(bean));
    } catch( NoSuchFieldException e) {
      // apparently the bean is not meant to handle property changes.
      LOG.warn("No propertyChangeSupport found on Bean: {}", bean.getClass());
    } catch ( SecurityException | IllegalArgumentException | IllegalAccessException e) {
      LOG.error("Error: ",e);
    }
    
  }
  
}
