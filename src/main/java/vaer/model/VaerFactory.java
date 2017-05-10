package vaer.model;

import vaer.view.VaerView;

public class VaerFactory {
	
	public static VaerModel getDefaultModel(String name, Class<? extends VaerView> viewClass){
			return getModelByClass(name, VaerModelImpl.class, viewClass);
	}
	
	public static VaerModel getModelByClass(String name, Class<? extends VaerModel> modelClass, Class<? extends VaerView> viewClass){
		try {
			VaerView view = viewClass.newInstance();
			view.initialize(name);
			
			VaerModel model = modelClass.newInstance();
			model.initialize(name, view);
			return model;
		} catch (IllegalAccessException | InstantiationException e) {
			e.printStackTrace();
			return null;
		}
	}
}
