package vaer.view.javafx;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
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
import java.util.Objects;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class VaerController extends Application implements VaerView, GroupView, NodeView<Node> {
	
	private static Boolean javaFxInitialized = false;
	private static Stage mainJavaFxStage;
	private final static ExecutorService javaFxThread = Executors.newSingleThreadExecutor();
	
	@FXML
	private VBox content;
	
	List<NodeView> childrens = new Vector<>();
	
	private Stage stage;
	
	protected String title;
	
	private Node node;
	
	private Boolean initialized = false;
	
	private Boolean resizing = false;
	
	public VaerController() {
	}
	
	public VaerController(String title) {
		initialize(title);
	}
	
	public void initialize(String title) {
		this.title = title;
		getNewStage();
		Platform.runLater(this::setUpStage);
		waitForInitialization();
	}
	
	private void getNewStage() {
		stage = initJavaFx();
		if (stage == null)
			Platform.runLater(() -> stage = new Stage());
	}
	
	protected synchronized Stage initJavaFx() {
		if (javaFxInitialized)
			return null;
		
		javaFxThread.submit(() ->
							{
								try {
									launch(); //invokes start()
								} catch (IllegalStateException e) {
									javaFxInitialized = true;
								}
							});
		
		Platform.setImplicitExit(false);
		waitForJavaFxInitialization();
		stage = mainJavaFxStage;
		return stage;
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		mainJavaFxStage = stage;
		javaFxInitialized = true;
	}
	
	private void setUpStage() {
		stage.setTitle(this.title);
		stage.getIcons().add(new Image(getClass().getResource("vaer_icon.png").toExternalForm()));
		stage.setOnCloseRequest(event -> close());
		Scene scene = getScene();
		stage.setScene(scene);
		stage.show();
		setUpStageResizing();
		stage.setWidth(500);
		stage.setHeight(400);
		initialized = true;
	}
	
	protected Scene getScene() {
		BorderPane rootPane = loadFXML();
		setNode(rootPane);
		Scene scene = new Scene(rootPane);
		scene.getStylesheets().add(getClass().getResource("modena_dark.css").toExternalForm());
		return scene;
	}
	
	private BorderPane loadFXML() {
		try {
			FXMLLoader vaer = new FXMLLoader(getClass().getResource("vaer.fxml"));
			vaer.setController(this);
			return vaer.load();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private void setUpStageResizing() {
		
		content.getChildren().addListener((ListChangeListener<Node>) c -> {
			stageResize();
		});
		
		content.layoutBoundsProperty().addListener((observable, oldValue, newValue) -> {
			if (!resizing)
				stageResize();
		});
		
		/*content.heightProperty().addListener((observable, oldValue, newValue) -> {
			if (!resizing)
				stage.setHeight(newValue.doubleValue());
		});*/
		
		stage.widthProperty().addListener((observable, oldValue, newValue) -> {
			resizing = !Objects.equals(oldValue.intValue(), newValue.intValue());
		});
		
		stage.heightProperty().addListener((observable, oldValue, newValue) -> {
			resizing = !Objects.equals(oldValue.intValue(), newValue.intValue());
		});
	}
	
	private void stageResize() {
		Platform.runLater(() -> {
			//stage.sizeToScene();
			//stage.setMinWidth(stage.getWidth());
			//stage.setMinHeight(stage.getHeight());
		});
	}
	
	@Override
	public GroupView group(String groupName) {
		GroupController groupView = NodesFactory.createGroupView(groupName);
		Platform.runLater(() -> content.getChildren().add(groupView.getNode()));
		groupView.setParent(this);
		childrens.add(groupView);
		return groupView;
	}
	
	@Override
	public VariableView variable(String variableName) {
		VariableController variableController = NodesFactory.createVariableView(variableName);
		Platform.runLater(() -> content.getChildren().add(variableController.getNode()));
		variableController.setParent(this);
		childrens.add(variableController);
		return variableController;
	}
	
	@Override
	public <T> void removeChild(NodeView<T> child) {
		childrens.remove(child);
		Platform.runLater(() -> content.getChildren().remove(child.getNode()));
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
	
	private void waitForJavaFxInitialization() {
		while (!javaFxInitialized) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void waitForInitialization() {
		while (!initialized) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
