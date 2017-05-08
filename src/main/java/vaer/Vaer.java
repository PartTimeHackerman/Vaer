package vaer;

import vaer.model.*;
import vaer.view.VaerView;
import vaer.view.javafx.VaerController;

import java.util.Optional;
import java.util.Vector;

public class Vaer {
	
	private final VaerModel model;
	private static Vaer mainVaer;
	private static final Vector<Vaer> vaerInstances = new Vector<>();
	private static final String defaultName = "Vaer";
	
	private String name;
	
	private Vaer(String name, VaerView view) {
		this.model = VaerFactory.getDefault(name, view);
		this.name = name;
	}
	
	public static Vaer get(final String name) {
		return get(name, new VaerController(name));
	}
	
	public static Vaer get(final String name, VaerView view) {
		Vaer vaer;
		
		if (name == null || name.equals(""))
			return get();
		
		synchronized (vaerInstances) {
			Optional<Vaer> vaerOptional = vaerInstances.stream()
					.filter(v -> v.getName().equals(name))
					.findFirst();
			vaer = vaerOptional.orElseGet(() -> new Vaer(name, view));
			if (mainVaer == null)
				mainVaer = vaer;
		}
		return vaer;
	}
	
	public static Vaer get() {
		if (mainVaer == null) {
			synchronized (Vaer.class) {
				if (mainVaer == null) {
					mainVaer = new Vaer(defaultName, new VaerController(defaultName));
				}
			}
		}
		return mainVaer;
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
}
