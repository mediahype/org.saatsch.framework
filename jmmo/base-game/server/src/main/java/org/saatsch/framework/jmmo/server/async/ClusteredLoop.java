package org.saatsch.framework.jmmo.server.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hazelcast.core.IAtomicLong;
import com.hazelcast.core.ILock;

import org.saatsch.framework.jmmo.cdi.container.JmmoContext;
import org.saatsch.framework.jmmo.clustering.ClusteringSupport;
import org.saatsch.framework.jmmo.timing.NonStopLoop;

/**
 * this is a {@link NonStopLoop} and as such, will run on every cluster member, however it's
 * abstract method {@link #doWork()} will only be invoked on one (random) member.
 *
 * Implementations must be ApplicationScoped and it is the implementations responsibility, that {@link #init()} is invoked.
 *
 */
public abstract class ClusteredLoop extends NonStopLoop {

  private static final Logger LOG = LoggerFactory.getLogger(ClusteredLoop.class);
  private static final String LOCK_KEY_SUFFIX = "-lock";
  private static final String COUNTER_KEY_SUFFIX = "-counter";

  private ClusteringSupport clusteringSupport = JmmoContext.getBean(ClusteringSupport.class);

  private IAtomicLong lastRun;

  private ILock lock;

  private Integer counter = 0;


  public void init() {
    lastRun = clusteringSupport.getAtomicLong(getName() + COUNTER_KEY_SUFFIX);
    lock = clusteringSupport.getLock(getName() + LOCK_KEY_SUFFIX);
    updateLastRun();
    start(true, getName(), 1, true);
  }

  @Override
  public void update() {
    if (counter >= getRunFrequency() && lastRunAgoSecs() >= getRunFrequency()) {
      try{
        guardWork();
      }catch (Exception e){
        LOG.error("Exception: {}", e.getMessage());
      }


      counter = 0;
    } else {
      counter++;
    }
  }

  private void guardWork() {
    if (lock.tryLock()) {
      try {
        doWork();
        updateLastRun();
      } finally {
        lock.unlock();
      }
    }
  }



  private long lastRunAgoSecs() {
    return (System.currentTimeMillis() - lastRun.get()) / 1000;
  }

  private void updateLastRun() {
    lastRun.set(System.currentTimeMillis());
  }

  protected abstract Integer getRunFrequency();

  /**
   * the name will be used for that Thread that runs the loop and for a cluster-wide counter and lock.
   * 
   * @return an application-unique name.
   */
  protected abstract String getName();

  /**
   * execute implementation specific logic.
   */
  protected abstract void doWork();

}
