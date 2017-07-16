package vaer.view;

public interface NodeView<T> {
	
	String getName();
	
	void setName(String name);
	
	T getNode();
	
	void setNode(T node);
	
	void close();
}
