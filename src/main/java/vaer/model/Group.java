package vaer.model;

import javafx.scene.Parent;

public interface Group extends Node {
	
	/*
	*	Get group if exists, otherwise create new with given name, append to parent and return
	 */
	Group group(String groupName);
	
	/*
	*	Get variable if exists, otherwise create new with given name, append to parent and return
	 */
	<T> Variable<T> variable(String variableName);
	
	/*
	*	Create new variable with given name, append to parent and return
	*	If variable with given name already exists, add postfix to variable name like "_i", where "i" is the number of repeated variable
	*	Example variable("var") -> var, variableNew("var") -> var_1, variableNew("var") -> var_2
	 */
	<T> Variable<T> variableNew(String variableName);
	
	void removeChild(Node child);
}
