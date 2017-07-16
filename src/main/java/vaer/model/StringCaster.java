package vaer.model;

public class StringCaster {
	
	@SuppressWarnings("unchecked")
	public static <T> T getValue(String input, Class clazz) {
		T variable = null;
		if (clazz.isAssignableFrom(String.class)) {
			variable = (T) input;
		} else if (clazz.isAssignableFrom(Integer.class)) {
			variable = (T) Integer.valueOf(input);
		} else if (clazz.isAssignableFrom(Boolean.class)) {
			variable = (T) Boolean.valueOf(input);
		} else if (clazz.isAssignableFrom(Double.class)) {
			variable = (T) Double.valueOf(input);
		} else if (clazz.isAssignableFrom(Long.class)) {
			variable = (T) Long.valueOf(input);
		} else if (clazz.isAssignableFrom(Byte.class)) {
			variable = (T) Byte.valueOf(input);
		} else if (clazz.isAssignableFrom(Character.class)) {
			variable = (T) Character.valueOf(input.charAt(0));
		} else if (clazz.isAssignableFrom(Short.class)) {
			variable = (T) Short.valueOf(input);
		} else if (clazz.isAssignableFrom(Float.class)) {
			variable = (T) Float.valueOf(input);
		}
		return variable;
	}
}
