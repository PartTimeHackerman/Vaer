package vaer.view.javafx;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import java.io.IOException;
import java.net.URL;

public class NodesFactory {
	
	private static final URL groupLoaderURL = NodesFactory.class.getResource("group.fxml");
	private static final URL variableLoaderURL = NodesFactory.class.getResource("variable.fxml");
	
	public static GroupController createGroupView(String name) {
		try {
			FXMLLoader groupLoader = new FXMLLoader(groupLoaderURL);
			Node groupNode = groupLoader.load();
			GroupController groupController = groupLoader.getController();
			groupController.setNode(groupNode);
			groupController.setName(name);
			return groupController;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static VariableController createVariableView(String name) {
		try {
			FXMLLoader variableLoader = new FXMLLoader(variableLoaderURL);
			Node variableNode = variableLoader.load();
			VariableController variableController = variableLoader.getController();
			variableController.setName(name);
			variableController.setNode(variableNode);
			return variableController;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
