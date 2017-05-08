package vaer.model;

import java.util.function.Consumer;
import java.util.function.Supplier;

public interface Variable<T> extends Child<Group>, Node {
	
	Variable<T> setVariableGetter(Supplier<T> supplier);
	Supplier<T> getVariableGetter();
	
	Variable<T> setVariableSetter(Consumer<T> consumer);
	Consumer<T> getVariableSetter();
	
	T getVariableValue();
	
	Variable<T> setVariableValue(T value);
	void setVariableValue(String value);
	
	Variable<T> setRefreshRate(Long refreshRate);
	
	void freeze(Boolean freeze);
}
