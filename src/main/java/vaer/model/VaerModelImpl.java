package vaer.model;

import vaer.view.VaerView;

class VaerModelImpl extends GroupAbstract implements Viewable<VaerView>, VaerModel {
	
	private String name;
	private VaerView view;
	
	public VaerModelImpl(final String name, VaerView view) {
		super();
		this.view = view;
		setName(name);
		setView(view);
		this.name = name;
	}
	
	@Override
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public GroupImpl group(String groupName) {
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
	public <T> VariableImpl<T> variableNew(String variableName) {
		VariableImpl<T> variable = super.variableNew(variableName);
		variable.setView(view.variable(name));
		return variable;
	}
	
	@Override
	public void setView(VaerView view) {
		this.view = view;
	}
	
	@Override
	public VaerView getView() {
		return view;
	}
}
