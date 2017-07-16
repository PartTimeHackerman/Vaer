package vaer.model;

interface Child<T> {
	
	T getParent();
	
	void setParent(T parent);
	
}
