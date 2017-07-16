package vaer.model.annotation;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

@SupportedAnnotationTypes("vaer.model.annotation.VaerVariable")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class VaerProcessor extends AbstractProcessor {
	
	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
		
		for (Element element : roundEnv.getElementsAnnotatedWith(VaerVariable.class)) {
			VaerVariable vaerAnnotation = element.getAnnotation(VaerVariable.class);
			String message = element.getSimpleName() + " " + vaerAnnotation.name();
			try {
				PrintWriter writer = new PrintWriter("itsjustfortests.txt", "UTF-8");
				writer.println(message);
				writer.close();
			} catch (IOException e) {
			}
			System.out.println(message);
			processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, message);
		}
		
		return true;
	}
}
