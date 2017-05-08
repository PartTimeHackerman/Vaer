package vaer.model;

import vaer.view.GroupView;

class GroupImpl extends GroupAbstract implements Viewable<GroupView>, Child<Group>{
	
	private String name;
	private GroupView view;
	private Group parent;
	
	GroupImpl() {
	}
	
	@Override
	public GroupImpl group(final String groupName) {
		GroupImpl group = super.group(groupName);
		if (group.getView() == null)
			group.setView(view.group(groupName));
		return group;
	}
	
	@Override
	public <T> VariableImpl<T> variable(final String variableName) {
		VariableImpl<T> variable = super.variable(variableName);
		if (variable.getView() == null)
			variable.setView(view.variable(variableName));
		return variable;
	}
	
	@Override
	public <T> VariableImpl<T> variableNew(final String variableName) {
		VariableImpl<T> variable = super.variableNew(variableName);
		if (variable.getView() == null)
			variable.setView(view.variable(variable.getName()));
		return variable;
	}
	
	
	@Override
	public void setParent(Group parent) {
		this.parent = parent;
	}
	
	@Override
	public Group getParent() {
		return parent;
	}
	
	@Override
	public void setView(GroupView view) {
		this.view = view;
		this.view.setGroup(this);
	}
	
	@Override
	public GroupView getView() {
		return view;
	}
	
	@Override
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String getName() {
		return this.name;
	}
	
	@Override
	public void dispose() {
		super.dispose();
		parent.removeChild(this);
		
	}
	
}
