# saatsch.framework.jmmo.core.pom

The core jmmo libraries do not contain any application specific logic.

The core libraries are

* [cdi](cdi/Readme.md)
  * depends on no other library
* client
  * depends on networking
* clustering
  * depends on cdi, networking and hazelcast
* data
  
* data.meta
  * has no dependencies; contains only annotations used in data modeling.
* networking
* timing
