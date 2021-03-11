## saatsch.framework.base.expression-base

pulls in the juel expression language (EL)

provides a small (optional) wrapper around it. 

The wrapper 
* has the ability to remember which keys are present in the namespace of the EL.
* maintains a list of listeners and informs them when a key (or a value associated with a key) has been changed.
