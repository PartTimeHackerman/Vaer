Vaer
===
Vaer is simple **va**riable watch**er**, allowing to watch, edit and freeze any variable in real time.
Default view is made in JavaFx, but it's easy to implement new one ( I'll implement another in JS in near future).

Basic usage:
```
Integer integer = 1;
...
Variable<Integer> vaeriable = Vaer.get()
					.group("Group 1")
					.group("Group 2")
					.variable("Int Variable");
vaeriable.setVariableSetter(this::set); //set integer setter
vaeriable.setVariableGetter(this::get); //set integer getter
vaeriable.setRefreshRate(200L); //set refresh rate in ms (default: 100ms)
```
Tips for variables:
```
//Get variable if exists, otherwise create new one with given name
variable("Int Variable");

//Create variable, if variable with given name already exists append _i to it's name (i is the number of repeated names)
variableNew("Int Variable");

//Getter/Freeze are invoked every refresh
setRefreshRate(200L);
```

How to use:
1. Go to https://jitpack.io/ 
2. ...
3. Done! :)

ToDo:
- implementation of browser GUI in JS,
- implementation of `````@VaerVariable````` annotation processor for easy watching,
- some refactoring
- non-primitives wrapper with methods like ```? get()``` and ```set(? t)```

