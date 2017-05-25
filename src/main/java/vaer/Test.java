package vaer;

import com.sun.webkit.plugin.PluginManager;
import vaer.model.TimeInterval;
import vaer.model.Variable;
import vaer.model.annotation.VaerVariable;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.stream.IntStream;

public class Test {
	
	Integer integer = 123;
	
	public static void main(String[] args) throws IOException {
		new Test().init();
	}
	
	public void init() throws IOException {
		
		IntStream.range(0, 10).forEach(i ->
									   {
										   Variable<Integer> v = Vaer.get()
												   .group("1")
												   .group("2")
												   .variableNew("varrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr");
										   v.setVariableSetter(this::set);
										   v.setVariableGetter(this::get);
									   });
		
		TimeInterval.addInterval(() -> integer++, 1000L);
		System.in.read();
	}
	
	
	private void set(Integer i2) {
		integer = i2;
	}
	
	private Integer get() {
		return integer;
	}
}
