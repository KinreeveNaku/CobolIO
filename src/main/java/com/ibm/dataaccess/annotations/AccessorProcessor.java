/**
 * 
 */
package com.ibm.dataaccess.annotations;

import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import javax.xml.ws.Action;

import org.slf4j.LoggerFactory;

import com.ibm.dataaccess.accessor.SerializableAccessor;

import ch.qos.logback.classic.Logger;

/**
 * {@link org.beanio.internal.config.annotation.AnnotationProcessor AnnotationProcessor}
 * @author Andrew
 * 
 */
public class AccessorProcessor extends AbstractProcessor {
	private static final Logger LOG = (Logger) LoggerFactory.getLogger(AccessorProcessor.class);
	private Types typeUtils;
	private Elements elementUtils;
	private Filer filer;
	private Map<String, AccessorAnnotatedClass> accessorClasses = new LinkedHashMap<>();
	private Messager messager;

	public AccessorProcessor() {
		super();

	}
	
	public <T extends SerializableAccessor> T createAccessor(Class<?> accessorClass) {
		return null;
	}

	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
		if (annotations.isEmpty()) {
			return false;
		}
		for (Element annotated : roundEnv.getElementsAnnotatedWith(Accessor.class)) {
			if (annotated.getKind() != ElementKind.CLASS) {
				LOG.error("Exception encountered with {}", annotated.getSimpleName());
				error(annotated, "@%s only applies to classes.", Accessor.class.getName());
				return true;
			}
			TypeElement typeElement = (TypeElement) annotated;
			try {
				AccessorAnnotatedClass accessorAnnotated = new AccessorAnnotatedClass(typeElement);
				if (!isValidClass(accessorAnnotated)) {
					return true;
				}
				accessorClasses.put(accessorAnnotated.getAccessor().getSimpleName(), accessorAnnotated);
			} catch (IllegalArgumentException e) {
				error(typeElement, e.getMessage());
				return true;
			}
		}
		return false;
	}

	@Action
	private boolean isValidClass(AccessorAnnotatedClass item) {

		// Cast to TypeElement, has more type specific methods
		TypeElement classElement = item.getAnnotatedClassElement();

		if (!classElement.getModifiers().contains(Modifier.PUBLIC)) {
			error(classElement, "The class %s is not public.", classElement.getQualifiedName().toString());
			return false;
		}

		// Check if it's an abstract class
		if (classElement.getModifiers().contains(Modifier.ABSTRACT)) {
			error(classElement, "The class %s is abstract. Abstract classes are incompatible with @%",
					classElement.getQualifiedName().toString(), Accessor.class.getSimpleName());
			return false;
		}

		// Check inheritance: Class must be childclass as specified in @Factory.type();
		TypeElement superClassElement = elementUtils.getTypeElement(SerializableAccessor.class.getCanonicalName());
		if (superClassElement.getKind() == ElementKind.INTERFACE) {
			// Check interface implemented
			if (!classElement.getInterfaces().contains(superClassElement.asType())) {
				error(classElement, "The class %s annotated with @%s must implement the interface %s",
						classElement.getQualifiedName().toString(), Accessor.class.getSimpleName(),
						SerializableAccessor.class.getName());
				return false;
			}
		} else {
			// Check subclassing
			TypeElement currentClass = classElement;
			while (true) {
				TypeMirror superClassType = currentClass.getSuperclass();

				if (superClassType.getKind() == TypeKind.NONE) {
					// Basis class (java.lang.Object) reached, so exit
					error(classElement, "The class %s annotated with @%s must inherit from %s",
							classElement.getQualifiedName().toString(), Accessor.class.getSimpleName(),
							SerializableAccessor.class.getSimpleName());
					return false;
				}

				if (superClassType.toString().equals(item.getAccessor().getCanonicalName())) {
					// Required super class found
					break;
				}

				// Moving up in inheritance tree
				currentClass = (TypeElement) typeUtils.asElement(superClassType);
			}
		}

		// Check if an empty public constructor is given
		for (Element enclosed : classElement.getEnclosedElements()) {
			if (enclosed.getKind() == ElementKind.CONSTRUCTOR) {
				ExecutableElement constructorElement = (ExecutableElement) enclosed;
				if (constructorElement.getParameters().isEmpty()
						&& constructorElement.getModifiers().contains(Modifier.PROTECTED)) {
					// Found an empty constructor
					return true;
				}
			}
		}

		// No empty constructor found
		error(classElement,
				"The class %s must provide an protected empty default constructor which can be invoked reflectively.",
				classElement.getQualifiedName().toString());
		return false;
	}

	private void error(Element e, String msg, Object... args) {
		messager.printMessage(Diagnostic.Kind.ERROR, String.format(msg, args), e);
	}

	private static class TypeInfo {
		boolean bound;
		Integer carg;
		String name;
		Class<?> type;
		Type genericType;

		String propertyName; // the class name of propertyType
		String collectionName;
		Class<?> propertyType;
		String getter;
		String setter;
	}
}
