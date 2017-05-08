package vaer.model;

interface Child<T> {
	
	void setParent(T parent);
	T getParent();
	
}
