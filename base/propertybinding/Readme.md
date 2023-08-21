pulling out propertybinding from javafx, first example is SimpleStringProperty.

Things we do not need from javafx:

* Bidirectional binding
* Change(Listener)
  * we only need invalidation.
* Beans's: 
  * getBean() gets the property's containing bean.
  * getName() gets the property's name.