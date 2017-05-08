package vaer.view.javafx;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Polygon;
import vaer.model.Group;
import vaer.view.GroupView;
import vaer.view.NodeView;
import vaer.view.VariableView;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;

public class GroupController implements GroupView, NodeView<Node> {
	
	@FXML
	private Label name;
	
	@FXML
	private HBox head;
	
	@FXML
	private Polygon wrapArrow;
	private Boolean open = true;
	
	@FXML
	private HBox contentsToHide;
	
	@FXML
	private VBox content;
	
	List<NodeView> childrens = new Vector<>();
	
	private Node groupNode;
	
	private GroupView parent;
	
	private Group group;
	
	@FXML
	public void initialize() {
		head.setOnMouseClicked(event -> {
			if (open) {
				open = false;
				wrapArrow.setRotate(90);
				contentsToHide.setVisible(false);
				contentsToHide.setManaged(false);
			} else {
				open = true;
				wrapArrow.setRotate(180);
				contentsToHide.setVisible(true);
				contentsToHide.setManaged(true);
			}
		});
	}
	
	@Override
	public GroupView group(String name) {
		GroupController groupController = NodesFactory.createGroupView(name);
		groupController.setParent(this);
		addChild(groupController);
		return groupController;
	}
	
	@Override
	public VariableView variable(String name) {
		VariableController variableController = NodesFactory.createVariableView(name);
		variableController.setParent(this);
		addChild(variableController);
		return variableController;
	}
	
	
	public void addChild(NodeView<Node> child) {
		Platform.runLater(() -> content.getChildren().add(child.getNode()));
		childrens.add(child);
	}
	
	@Override
	public <T> void removeChild(NodeView<T> child) {
		childrens.remove(child);
		Platform.runLater(() -> content.getChildren().remove(child.getNode()));
	}
	
	@Override
	public void setGroup(Group group) {
		this.group = group;
		
	}
	
	@Override
	public void close() {
		for (int i = 0; i < childrens.size(); i++) {
			NodeView children = childrens.get(i);
			children.close();
		}
		group.dispose();
		parent.removeChild(this);
	}
	
	
	@Override
	public void setName(String name) {
		this.name.setText(name);
	}
	
	@Override
	public String getName() {
		return this.name.getText();
	}
	
	@Override
	public void setNode(Node node) {
		this.groupNode = node;
	}
	
	@Override
	public Node getNode() {
		return groupNode;
	}
	
	public void setParent(GroupView parent) {
		this.parent = parent;
	}
	
	public GroupView getParent() {
		return parent;
	}
}
