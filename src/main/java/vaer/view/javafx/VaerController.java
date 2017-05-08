package vaer.view.javafx;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import vaer.model.Group;
import vaer.view.NodeView;
import vaer.view.VaerView;
import vaer.view.GroupView;
import vaer.view.VariableView;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;
import java.util.concurrent.Executors;

public class VaerController extends Application implements VaerView, GroupView, NodeView<Node> {
	
	private static Stage mainStage;
	
	@FXML
	private VBox content;
	
	List<NodeView> childrens = new Vector<>();
	
	private Stage stage;
	
	private String title;
	
	private Node node;
	
	public VaerController(){}
	
	public VaerController(String title) {
		this.title = title;
		if (mainStage != null) {
			Platform.runLater(() -> initStage(new Stage()));
		} else {
			initMainStage();
			Platform.runLater(() -> initStage(mainStage));
		}
	}
	
	private void initMainStage() {
		Executors.newSingleThreadExecutor().submit(() -> {
			try {
				launch();
			} catch (IllegalStateException e) {
				Platform.runLater(() -> mainStage = new Stage());
			}
		});
		while (mainStage == null) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		mainStage = stage;
	}
	
	private void initStage(Stage stage) {
		try {
			this.stage = stage;
			stage.setTitle(this.title);
			FXMLLoader vaer = new FXMLLoader(getClass().getResource("vaer.fxml"));
			vaer.setController(this);
			BorderPane rootPane = vaer.load();
			setNode(rootPane);
			Scene scene = new Scene(rootPane);
			scene.getStylesheets().add(getClass().getResource("modena_dark.css").toExternalForm());
			stage.getIcons().add(new Image(getClass().getResource("vaer_icon.png").toExternalForm()));
			stage.setOnCloseRequest(event -> close());
			stage.setScene(scene);
			stage.show();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	@Override
	public GroupView group(String groupName) {
		GroupController groupView = NodesFactory.createGroupView(groupName);
		Platform.runLater(() -> content.getChildren().add(groupView.getNode()));
		groupView.setParent(this);
		childrens.add(groupView);
		groupView.getNode().boundsInParentProperty().addListener(observable -> {
			Platform.runLater(() -> stage.sizeToScene());
		});
		return groupView;
	}
	
	@Override
	public VariableView variable(String variableName) {
		VariableController variableController = NodesFactory.createVariableView(variableName);
		content.getChildren().add(variableController.getNode());
		childrens.add(variableController);
		return variableController;
	}
	
	@Override
	public <T> void removeChild(NodeView<T> child) {
		childrens.remove(child);
		Platform.runLater(()-> content.getChildren().remove(child.getNode()));
	}
	
	@Override
	public void setGroup(Group group) {
	
	}
	
	@Override
	public void setName(String name) {
		this.title = name;
	}
	
	@Override
	public String getName() {
		return title;
	}
	
	@Override
	public void setNode(Node node) {
		this.node = node;
	}
	
	@Override
	public Node getNode() {
		return node;
	}
	
	@Override
	public void close() {
		for (int i = 0; i < childrens.size(); i++) {
			NodeView children = childrens.get(i);
			children.close();
		}
	}
	
}
