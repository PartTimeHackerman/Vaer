package vaer.model;


interface Viewable<T> {
	
	T getView();
	
	void setView(T view);
}
