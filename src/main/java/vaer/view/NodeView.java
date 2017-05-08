package vaer.view;

import javafx.scene.Node;

public interface NodeView<T> {
	
	void setName(String name);
	String getName();
	
	void setNode(T node);
	T getNode();
	
	void close();
}
