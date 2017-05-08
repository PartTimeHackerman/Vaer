package vaer.view;

import vaer.model.Variable;

public interface VariableView {
	
	<T> void setValue(T value);
	
	void setVariableValue();
	
	void freeze(Boolean freeze);
	
	<T> void setRefreshRate(T value);
	
	void setVariableRefreshRate(Long refreshRate);
	
	<T> void setVariable(Variable<T> variable);
	
}
