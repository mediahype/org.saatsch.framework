package org.saatsch.framework.jmmo.clustering;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IAtomicLong;
import com.hazelcast.core.IExecutorService;
import com.hazelcast.core.ILock;
import com.hazelcast.core.IMap;
import com.hazelcast.core.ITopic;
import com.hazelcast.core.Member;
import com.hazelcast.core.MultiMap;

import org.saatsch.framework.jmmo.cdi.container.JmmoContext;
import org.saatsch.framework.jmmo.cdi.container.Lazy;

public class ClusteringSupport {

  private static final Logger LOG = LoggerFactory.getLogger(ClusteringSupport.class);
  private final Config config = new Config();
  
  private Lazy<HazelcastInstance> h = Lazy.of(() -> 
  Hazelcast.newHazelcastInstance(config));
  
  private Lazy<IExecutorService> ex = Lazy.of(() -> h.get().getExecutorService("my-distributed-executor")) ;
  
  private Exec localExecutorPool = JmmoContext.getBean(Exec.class);
  
  private Lazy<Member> localMember = Lazy.of(() -> h.get().getCluster().getLocalMember());

  public ClusteringSupport() {
    // get an own group name so to not interfere with other hazelcast apps on the network that use the default group.
    config.getGroupConfig().setName("tommy");
   
  }
  

  public void executeLocally(Runnable command) {
    // TODO: do not use this extra thread pool
    localExecutorPool.exec(command);
  }

  public void executeOnMember(Runnable command, String memberUUID) {
    for (Member member : hazel().getCluster().getMembers()) {
      if (member.getUuid().equals(memberUUID)) {
        ex.get().executeOnMember(command, member);
      }
    }
  }

  private HazelcastInstance hazel() {
    return h.get();
  }
  
  /**
   * @return the UUID of the local cluster member.
   */
  public String getLocalMemberUUID() {
    return localMember.get().getUuid();
  }

  public <T> ITopic<T> getTopic(String topicName, Class<T> topicMessageType) {
    return hazel().getTopic(topicName);
  }

  public <K, V> IMap<K, V> getMap(String mapName) {
    return hazel().getMap(mapName);
  }

  public <K, V> MultiMap<K, V> getMultimap(String name) {
    return hazel().getMultiMap(name);
  }

  /**
   * delegates to {@link HazelcastInstance#getLock(String)}
   * 
   * @param key
   * @return
   */
  public ILock getLock(String key) {
    return hazel().getLock(key);
  }
  
  public IAtomicLong getAtomicLong(String name) {
    return hazel().getAtomicLong(name);
  }
  
}
