#### jface-base provides:

* beantree (composite) 
  * see [BeanTreeDemo](src/test/java/org/saatsch/framework/base/swt/widgets/beantree/BeanTreeDemo.java) 

* logintercept (composite)
  * intercepts app's logging and writes it to an SWT List.
  * The solution only works when using simplelogging (slf4j-simple)
  * also see LogInterceptDemo

* SafeAppLoop (Utility Class)
  * use this so your SWT app will not crash on RuntimeExceptions.
  * also see SafeAppLoopDemo

* AbstractTreeRenamer

* IconFontSwt
  * Create an SWT Image from a font-awesome character.
  * also see IconFontSwtDemo

* etc...
