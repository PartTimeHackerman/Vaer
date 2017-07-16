package vaer.model;

import vaer.view.VariableView;

import java.util.function.Consumer;
import java.util.function.Supplier;

class VariableImpl<T> implements Variable<T>, Viewable<VariableView> {
	
	private String name;
	private VariableView view;
	
	private Supplier<T> variableGetter;
	private Consumer<T> variableSetter;
	private Group parent;
	
	private T variable;
	private TimeInterval.Job refresher;
	private TimeInterval.Job freezer;
	
	private Boolean freezed = false;
	private Long refreshRate = 100L;
	
	public VariableImpl() {
	}
	
	@Override
	public String getName() {
		return this.name;
	}
	
	@Override
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public Variable<T> setVariableGetter(Supplier<T> supplier) {
		this.variableGetter = supplier;
		setRefresher(true);
		return this;
	}
	
	@Override
	public Supplier<T> getVariableGetter() {
		return this.variableGetter;
	}
	
	@Override
	public Variable<T> setVariableSetter(Consumer<T> consumer) {
		this.variableSetter = consumer;
		view.editable(true);
		return this;
	}
	
	@Override
	public Consumer<T> getVariableSetter() {
		return this.variableSetter;
	}
	
	@Override
	public T getVariableValue() {
		return variableGetter.get();
	}
	
	@Override
	public void setVariableValue(String value) {
		variable = StringCaster.getValue(value, variable.getClass());
		if (variableSetter != null)
			variableSetter.accept(variable);
	}
	
	@Override
	public Variable<T> setVariableValue(T value) {
		variableSetter.accept(value);
		return this;
	}
	
	@Override
	public Variable<T> setRefreshRate(Long refreshRate) {
		this.refreshRate = refreshRate;
		view.setRefreshRate(this.refreshRate);
		if (freezed)
			setFreezer(true);
		setRefresher(true);
		return this;
	}
	
	@Override
	public void freeze(Boolean freeze) {
		this.freezed = freeze;
		setFreezer(freeze);
	}
	
	@Override
	public void dispose() {
		if (freezer != null)
			freezer.cancel();
		if (refresher != null)
			refresher.cancel();
		
		parent.removeChild(this);
	}
	
	private void setFreezer(Boolean on) {
		
		if (!on && freezer != null) {
			freezer.cancel();
			return;
		}
		
		if (freezer != null)
			freezer.cancel();
		
		if (getVariableSetter() != null)
			freezer = TimeInterval.addInterval(this::updateFreeze, refreshRate);
		else
			System.out.println("No setter!");
	}
	
	private void setRefresher(Boolean on) {
		
		if (!on && refresher != null) {
			refresher.cancel();
			return;
		}
		
		if (refresher != null)
			refresher.cancel();
		
		if (getVariableGetter() != null)
			refresher = TimeInterval.addInterval(this::updateView, refreshRate);
		else
			System.out.println("No getter!");
	}
	
	private void updateFreeze() {
		if (variableSetter != null)
			variableSetter.accept(variable);
	}
	
	private void updateView() {
		if (variableGetter == null)
			return;
		
		T var = variableGetter.get();
		if (!freezed)
			variable = var;
		view.setValue(var);
	}
	
	@Override
	public Group getParent() {
		return parent;
	}
	
	@Override
	public void setParent(Group parent) {
		this.parent = parent;
	}
	
	@Override
	public VariableView getView() {
		return view;
	}
	
	@Override
	public void setView(VariableView view) {
		this.view = view;
		this.view.setVariable(this);
		this.view.setRefreshRate(refreshRate);
		this.view.editable(false);
	}
}
























