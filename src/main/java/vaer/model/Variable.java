package vaer.model;

import java.util.function.Consumer;
import java.util.function.Supplier;

public interface Variable<T> extends Child<Group>, Node {
	
	Supplier<T> getVariableGetter();
	
	Variable<T> setVariableGetter(Supplier<T> supplier);
	
	Consumer<T> getVariableSetter();
	
	Variable<T> setVariableSetter(Consumer<T> consumer);
	
	T getVariableValue();
	
	void setVariableValue(String value);
	
	Variable<T> setVariableValue(T value);
	
	Variable<T> setRefreshRate(Long refreshRate);
	
	void freeze(Boolean freeze);
}
