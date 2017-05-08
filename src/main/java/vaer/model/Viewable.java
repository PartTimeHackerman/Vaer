package vaer.model;


interface Viewable<T> {
	
	void setView(T view);
	T getView();
}
