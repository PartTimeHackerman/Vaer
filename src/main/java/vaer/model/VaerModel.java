package vaer.model;

import vaer.model.Group;
import vaer.model.Variable;
import vaer.view.VaerView;

public interface VaerModel {
	
	Group group(String groupName);
	
	<T> Variable<T> variable(String variableName);
	
	<T> Variable<T> variableNew(String variableName);
	
	void initialize(String name, VaerView view);
}
