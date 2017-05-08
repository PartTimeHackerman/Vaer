package vaer.model.annotation;

import spoon.processing.AbstractAnnotationProcessor;
import spoon.reflect.declaration.CtField;

import java.io.IOException;
import java.io.PrintWriter;

public class SpoonProcessor extends AbstractAnnotationProcessor<VaerVariable, CtField> {
	@Override
	public void process(VaerVariable annotation, CtField element) {
		try{
			PrintWriter writer = new PrintWriter("the-file-name.txt", "UTF-8");
			writer.println(element.toString());
			writer.close();
		} catch (IOException e) {
			// do something
		}
	}
}
