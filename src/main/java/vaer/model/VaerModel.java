package vaer.model;

import vaer.model.Group;
import vaer.model.Variable;

public interface VaerModel {
	
	Group group(String groupName);
	
	<T> Variable<T> variable(String variableName);
}
