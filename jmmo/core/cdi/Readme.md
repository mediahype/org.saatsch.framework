# saatsch.framework.jmmo.core.cdi

extremely simple CDI framework that only knows application scoped objects and provides static access to them.

It has only 2 classes:
* [JmmoContext](src/main/java/org/saatsch/framework/jmmo/cdi/container/JmmoContext.java)
* [Lazy](src/main/java/org/saatsch/framework/jmmo/cdi/container/Lazy.java)
   * needed, when you have a cycle among the objects you wish to store in the context.

