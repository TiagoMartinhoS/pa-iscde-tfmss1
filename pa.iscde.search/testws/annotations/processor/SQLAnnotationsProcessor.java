package pa.sqlgen.annotations.processor;


import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic.Kind;

import pa.sqlgen.annotations.ForeignKey;
import pa.sqlgen.annotations.PrimaryKey;
import pa.sqlgen.annotations.Table;

@SupportedAnnotationTypes("pa.sqlgen.annotations.*")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class SQLAnnotationsProcessor extends AbstractProcessor {

	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment env) {
		
		for (Element e : env.getElementsAnnotatedWith(Table.class))
			validateTable((TypeElement) e);
			
		for (Element e : env.getElementsAnnotatedWith(PrimaryKey.class))
				validatePrimaryKey((VariableElement) e);
		
		for (Element e : env.getElementsAnnotatedWith(ForeignKey.class))
				validateForeignKey((VariableElement) e);
	
		return true;
	}

	private void addError(Element e, String message) {
		processingEnv.getMessager().printMessage(Kind.ERROR, message, e);		
	}
	
	private void validateTable(TypeElement type) {
		Table annotation = type.getAnnotation(Table.class);
		ElementKind kind = type.getKind();
		
		if (kind.isClass()) {
			for (Character c : annotation.value().toCharArray()) {
				if (!Character.isUpperCase(c) && c != '_') {
					addError(type, "Table name is not valid. Only uppercase and _ are accepted.");
					break;
				}
			}
		} else {
			addError(type, "Element is not valid. Only classes are allowed and your ElementKind is " + kind);
		}
		
		

	}
		
	private void validatePrimaryKey(VariableElement field) {
		TypeMirror type = field.asType();
		String typeName = type.toString();
		
		if (!typeName.equals(int.class.getName()) && !typeName.equals(String.class.getName())) {
			addError(field, "Your typeName is " + typeName + 
					" while the allowed typeNames are " + int.class.getName() + " and " + String.class.getName());
		}
		
	}
	
	private void validateForeignKey(Element e) {
		boolean found = false;
		TypeMirror typeMirror = getTypeMirror(e.getAnnotation(ForeignKey.class)); //class of outer table
		TypeElement typeElement = asTypeElement(typeMirror);
		if (typeElement.getAnnotation(Table.class) != null) {
			for (Element element : typeElement.getEnclosedElements()) {
				if (element.getKind().isField()) {
					if (element.getAnnotation(PrimaryKey.class) != null) {
						if (element.getSimpleName().toString().equals(e.getAnnotation(ForeignKey.class).field())) {
							found = true;
							break;
						}
					}
				}
			}
		} else {
			addError(e, "No @Table annotation found on the referenced class");
			return;
		}
		if (!found) {
			addError(e, "No @PrimaryKey annotation found on the referenced field or the field does not exist");
		}
		
	}
	
	private TypeElement asTypeElement(TypeMirror typeMirror) {
	    Types TypeUtils = this.processingEnv.getTypeUtils();
	    return (TypeElement)TypeUtils.asElement(typeMirror);
	}
	
	private TypeMirror getTypeMirror(ForeignKey annotation) {
	    try
	    {
	        annotation.table(); // this should throw
	    }
	    catch( MirroredTypeException mte )
	    {
	        return mte.getTypeMirror();
	    }
	    return null;
	}
}
