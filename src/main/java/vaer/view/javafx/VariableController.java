package vaer.view.javafx;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import vaer.model.Variable;
import vaer.view.GroupView;
import vaer.view.NodeView;
import vaer.view.VariableView;

public class VariableController implements VariableView, NodeView<Node> {
	
	@FXML
	private Label variableNameLabel;
	
	@FXML
	private TextField valueTextField;
	
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
		
		valueTextField.focusedProperty().addListener((observable, oldValue, focused) -> {
			if (focused) {
				valueTextField.setPromptText(valueTextField.getText().equals("") ? valueTextField.getPromptText() : valueTextField.getText());
				valueTextField.setText("");
			} else if (!valueTextField.getText().equals("")) {
				String val = valueTextField.getText();
				setVariableValue(val);
			}
		});
		
		refreshRate.focusedProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue) {
				try {
					Long newRefreshRate = Long.parseLong(refreshRate.getText());
					if (newRefreshRate > 0)
						setVariableRefreshRate(newRefreshRate);
				} catch (Exception e) {
				}
			}
		});
	}
	
	public Label getNameLabel() {
		return variableNameLabel;
	}
	
	public TextField getValueField() {
		return valueTextField;
	}
	
	public GroupView getParent() {
		return parent;
	}
	
	public void setParent(GroupView parent) {
		this.parent = parent;
	}
	
	@Override
	public <T> void setValue(T value) {
		synchronized (this.valueTextField) {
			Platform.runLater(() -> {
				if (!this.valueTextField.isFocused())
					this.valueTextField.setText(value.toString());
				else
					this.valueTextField.setPromptText(value.toString());
			});
		}
	}
	
	@Override
	public void setVariableValue() {
		String input = this.valueTextField.getText();
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
	public void editable(Boolean editable) {
		valueTextField.setEditable(editable);
		valueTextField.setMouseTransparent(!editable);
		freeze.setDisable(!editable);
	}
	
	@Override
	public void close() {
		variable.dispose();
		parent.removeChild(this);
	}
	
	@Override
	public String getName() {
		return this.variableNameLabel.getText();
	}
	
	@Override
	public void setName(String name) {
		this.variableNameLabel.setText(name);
	}
	
	@Override
	public Node getNode() {
		return variableNode;
	}
	
	@Override
	public void setNode(Node node) {
		this.variableNode = node;
	}
}
