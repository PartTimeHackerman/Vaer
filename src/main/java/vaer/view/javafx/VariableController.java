package vaer.view.javafx;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import vaer.view.GroupView;
import vaer.view.NodeView;
import vaer.view.VariableView;
import vaer.model.Variable;

public class VariableController implements VariableView, NodeView<Node> {
	
	@FXML
	private Label name;
	
	@FXML
	private TextField value;
	
	@FXML
	private CheckBox freeze;
	
	@FXML
	private TextField refreshRate;
	
	@FXML
	private Button closeButton;
	
	private Variable variable;
	private Node variableNode;
	private GroupView parent;
	
	@FXML
	public void initialize() {
		
		closeButton.setOnAction(event -> {
			close();
		});
		
		freeze.selectedProperty().addListener((observable, oldValue, newValue) -> {
			freeze(newValue);
		});
		
		//value.setStyle("-fx-prompt-text-fill: derive(-fx-control-inner-background, -50%);");
		
		value.selectedTextProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.equals(""))
				variable.setVariableValue(newValue);
		});
		
		value.focusedProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue)
				value.setText("");
			else if (!value.getText().equals("")) {
				String val = value.getText();
				setVariableValue(val);
			}
		});
		
		refreshRate.setOnKeyReleased(event -> {
			if (event.getCode().equals(KeyCode.ENTER)) {
				try {
					Long refreshR = Long.parseLong(refreshRate.getText());
					setVariableRefreshRate(refreshR);
				} catch (Exception e) {
				}
			}
		});
	}
	
	public Label getNameLabel() {
		return name;
	}
	
	public TextField getValueField() {
		return value;
	}
	
	public void setParent(GroupView parent) {
		this.parent = parent;
	}
	
	public GroupView getParent() {
		return parent;
	}
	
	@Override
	public <T> void setValue(T value) {
		synchronized (this.value) {
			if (!this.value.isFocused())
				Platform.runLater(() -> this.value.setText(value.toString()));
			else
				Platform.runLater(() -> this.value.setPromptText(value.toString()));
		}
	}
	
	@Override
	public void setVariableValue() {
		String input = this.value.getText();
		if (!input.equals(""))
			variable.setVariableValue(input);
	}
	
	public void setVariableValue(String val) {
		variable.setVariableValue(val);
	}
	
	@Override
	public void freeze(Boolean freeze) {
		variable.freeze(freeze);
		setVariableValue();
	}
	
	@Override
	public <T> void setRefreshRate(T value) {
		synchronized (this.refreshRate) {
			this.refreshRate.setText(value.toString());
		}
	}
	
	@Override
	public void setVariableRefreshRate(Long refreshRate) {
		variable.setRefreshRate(refreshRate);
	}
	
	@Override
	public <T> void setVariable(Variable<T> variable) {
		this.variable = variable;
	}
	
	@Override
	public void close() {
		variable.dispose();
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
		this.variableNode = node;
	}
	
	@Override
	public Node getNode() {
		return variableNode;
	}
}
