package vaer;

import org.junit.Test;
import vaer.model.TimeInterval;
import vaer.model.Variable;
import vaer.model.annotation.VaerVariable;

import java.io.IOException;
import java.util.function.Consumer;
import java.util.stream.IntStream;

public class VaerTest {
	
	Integer integer = 1;
	
	@Test
	public void createStage() throws IOException {
		Variable<Integer> v = Vaer.get()
				.group("Group 1")
				.group("Group 2")
				.variableNew("Int Variable");
		v.setVariableGetter(this::get);
		
		IntStream.range(0, 10).forEach(i ->
									   {
										   Variable<Integer> vaeriable = Vaer.get()
												   .group("Group 1")
												   .group("Group 2")
												   .variableNew("Int Variable");
										   vaeriable.setVariableSetter(this::set);
										   vaeriable.setVariableGetter(this::get);
										   vaeriable.setRefreshRate(100L);
									   });
		
		IntStream.range(0, 3).forEach(i ->
									   {
										   Variable<Integer> vaeriable = Vaer.get("New window").variableNew("Int Variable");
										   vaeriable.setVariableSetter(this::set);
										   vaeriable.setVariableGetter(this::get);
										   vaeriable.setRefreshRate(100L);
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