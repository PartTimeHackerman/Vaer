package vaer;

import vaer.model.Group;
import vaer.model.VaerFactory;
import vaer.model.VaerModel;
import vaer.model.Variable;
import vaer.view.VaerView;
import vaer.view.javafx.VaerController;

import java.util.Optional;
import java.util.Vector;

public class Vaer {
	
	private static final Vector<Vaer> vaerInstances = new Vector<>();
	private static Vaer mainVaer;
	private static String defaultName = "Vaer";
	private static Class<? extends VaerView> defaultView = VaerController.class;
	
	private final VaerModel model;
	private final String name;
	
	private Vaer(String name, Class<? extends VaerView> viewClass) {
		this.model = VaerFactory.getDefaultModel(name, viewClass);
		this.name = name;
	}
	
	
	public static Vaer get() {
		return get(defaultName, defaultView);
	}
	
	public static Vaer get(final String name) {
		return get(name, defaultView);
	}
	
	public static Vaer get(Class<? extends VaerView> viewClass) {
		return get(defaultName, viewClass);
	}
	
	public static Vaer get(final String name, Class<? extends VaerView> viewClass) {
		Vaer vaer;
		
		if (name == null || name.equals(""))
			return get(defaultName, viewClass);
		
		synchronized (vaerInstances) {
			Optional<Vaer> vaerOptional = vaerInstances.stream()
					.filter(v -> v.getName().equals(name))
					.findFirst();
			vaer = vaerOptional.orElseGet(() -> new Vaer(name, viewClass));
			if (mainVaer == null)
				mainVaer = vaer;
			vaerInstances.add(vaer);
		}
		return vaer;
	}
	
	public static String getDefaultName() {
		return defaultName;
	}
	
	public static void setDefaultName(String defaultName) {
		Vaer.defaultName = defaultName;
	}
	
	public static Class<? extends VaerView> getDefaultView() {
		return defaultView;
	}
	
	public static void setDefaultView(Class<? extends VaerView> defaultView) {
		Vaer.defaultView = defaultView;
	}
	
	public Group group(String groupName) {
		return model.group(groupName);
	}
	
	public <T> Variable<T> variable(String variableName) {
		return model.variable(variableName);
	}
	
	public String getName() {
		return name;
	}
	
	public Variable<Integer> variableNew(String variableName) {
		return model.variableNew(variableName);
	}
}
