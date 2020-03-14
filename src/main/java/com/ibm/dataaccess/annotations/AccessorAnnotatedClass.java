/**
 * 
 */
package com.ibm.dataaccess.annotations;

import java.util.Arrays;

import javax.lang.model.element.TypeElement;

import org.slf4j.LoggerFactory;

import com.ibm.dataaccess.accessor.SerializableAccessor;
import com.ibm.dataaccess.accessor.impl.AnyAsNull;

import ch.qos.logback.classic.Logger;

/**
 * @author Andrew
 *
 */
@Deprecated
public class AccessorAnnotatedClass {
	private static final Logger LOG = (Logger)LoggerFactory.getLogger(AccessorAnnotatedClass.class);
	private TypeElement annotatedClassElement;
	private Class<?> accessor;
	private Class<?> incoming;
	private Class<?> outgoing;
	
	public AccessorAnnotatedClass(TypeElement classElement) throws IllegalArgumentException {
		this.annotatedClassElement = classElement;
		Accessor annotation = classElement.getAnnotation(Accessor.class);
		accessor = annotation.accessor();
		incoming = annotation.incoming();
		outgoing = annotation.outgoing();
		if(Arrays.binarySearch(accessor.getInterfaces(), SerializableAccessor.class) < 0) {
			throw new IllegalArgumentException("Accessors must implement the ObjectAccessor interface.");
		}
		if(incoming == null || outgoing == null || incoming.equals(Object.class) || outgoing.equals(Object.class)) {
			throw new IllegalArgumentException("Types specified in the Accessor annotation must be non-null and cannot be the Object class.");
		}
		if(incoming.equals(Void.class) || incoming.equals(void.class)) {
			throw new IllegalArgumentException("The incoming attribute may not be designated to void");
		}
		if(outgoing.equals(Void.class) || outgoing.equals(void.class)) {
			accessor = AnyAsNull.class;
		}
	}

	public Class<?> getAccessor() {
		return this.accessor;
	}

	public Class<?> getIncoming() {
		return this.incoming;
	}

	public Class<?> getOutgoing() {
		return this.outgoing;
	}

	public TypeElement getAnnotatedClassElement() {
		return annotatedClassElement;
	}
}
/*
 * 000000000001 = 000000000001 000000001010 = 000000000010 000000010100 =
 * 000000010100 000011001000 = 000011001000 000110010000 = 000110010000
 * 001111101000 = 000000101000 011111010000 = 000001010000 111110100000 =
 * 100000000000
 * 
 * 
 * public static final int PUBLIC 		1 
 * public static final int PRIVATE 		2 
 * public static final int PROTECTED 	4 
 * 
 * public static final int STATIC 		8 
 * public static final int FINAL 		16 
 * public static final int SUPER 		32
 * public static final int SYNCHRONIZED 32 
 * public static final int VOLATILE		64
 * public static final int BRIDGE 		64 
 * public static final int TRANSIENT	128 
 * public static final int VARARGS		128 
 * public static final int NATIVE 		256 
 * public static final int INTERFACE 	512 
 * public static final int ABSTRACT 	1024 
 * public static final int STRICT 		2048 
 * public static final int SYNTHETIC 	4096 
 * public static final int ANNOTATION 	8192
 * public static final int ENUM 		16384 
 * public static final int MANDATED 	32768 
 * public static final int MODULE 		32768 
 */