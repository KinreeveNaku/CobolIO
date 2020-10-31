/**
 * 
 */
package com.github.cobolio.internal.compiler;

/**
 * @author Andrew
 *
 */
public class CompilerKeys {
	public static final String CLASS = "this.class";
	public static final String CLASS_NAME = "this.class.name";//java.lang.String
	public static final String PACKAGE_NAME = "this.class.package";//java.lang.String
	public static final String CLASS_SUPER = "this.class.extends";//CtClass
	public static final String CLASS_INTERFACES = "this.class.implements";//java.util.List<java.lang.CtClass>
	public static final String CLASS_ANNOTS = "this.class.annotations";//java.util.List<CtAnnotation>
	public static final String CLASS_IMPORTS = "this.class.imports";//java.util.List<java.lang.String>
	public static final String CLASS_INNERS = "this.class.innerClasses";//java.util.Map<String, CtClass> -- cyclic
	public static final String CLASS_FIELDS = "this.class.fields";//java.util.Map<String, CtField>
	public static final String CLASS_METHODS = "this.class.methods";//java.util.Map<String, CtMethod>
}
