package vaer.model;

import java.util.Optional;
import java.util.Vector;

abstract class GroupAbstract implements Group {
	
	private final Vector<Node> childrens = new Vector<>();
	
	public GroupImpl group(final String groupName) {
		GroupImpl group;
		
		Optional<GroupImpl> groupOptional = childrens.stream()
				.filter(node ->
								node instanceof GroupImpl
										&& node.getName().equals(groupName))
				.map(node -> (GroupImpl) node)
				.findFirst();
		
		group = groupOptional.orElseGet(() -> {
			GroupImpl newGroup = new GroupImpl();
			childrens.add(newGroup);
			newGroup.setParent(this);
			newGroup.setName(groupName);
			return newGroup;
		});
		return group;
	}
	
	public <T> VariableImpl<T> variable(final String variableName) {
		VariableImpl<T> variable;
		Optional<VariableImpl<T>> groupOptional = getVariable(variableName);
		variable = groupOptional.orElseGet(() -> createNew(variableName));
		return variable;
	}
	
	public <T> VariableImpl<T> variableNew(final String variableName) {
		Integer counter = 0;
		String name = variableName;
		while (getVariable(name).isPresent()){
			counter++;
			name = variableName + "_" + counter;
		}
		
		return createNew(name);
	}
	
	protected <T> Optional<VariableImpl<T>> getVariable(final String variableName) {
		return childrens.stream()
				.filter(node ->
								node instanceof VariableImpl
										&& node.getName().equals(variableName))
				.map(node -> (VariableImpl<T>) node)
				.findFirst();
	}
	
	protected <T> VariableImpl<T> createNew(final String variableName) {
		VariableImpl<T> variable = new VariableImpl<>();
		childrens.add(variable);
		variable.setName(variableName);
		variable.setParent(this);
		return variable;
	}
	
	
	@Override
	public void removeChild(Node child) {
		childrens.remove(child);
	}
	
	
	@Override
	public void dispose() {
		for (int i = 0; i < childrens.size(); i++) {
			Node children = childrens.get(i);
			children.dispose();
		}
	}
}
