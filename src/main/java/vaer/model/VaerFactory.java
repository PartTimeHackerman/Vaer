package vaer.model;

import vaer.view.VaerView;

public class VaerFactory {
	
	public static VaerModel getDefault(String name, VaerView view){
		return new VaerModelImpl(name, view);
	}
}
