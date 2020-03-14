/**
 * 
 */
package com.github.mfds2j.classgen;

import java.lang.reflect.InvocationTargetException;

import org.apache.avro.generic.GenericData.StringType;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import com.github.mfds2j.classgen.registry.Conversion;

/**
 * @author Andrew
 *
 */
public class VirtualCompiler {
	private boolean createSetters;
	private String suffix;
	private boolean enableDecimalLogicalType;
	private boolean createAllArgsConstructor;
	private boolean gettersReturnOptional;
	private boolean createOptionalGetters;
	private FieldVisibility fieldVisibility;
	private int maxStringChars;
	private String templateDir;
	private VelocityContext context;
	private StringType stringType;
	private VelocityEngine velocityEngine;
	private VirtualData virtualData;

	VirtualCompiler() {
		this.context = new VelocityContext();
		this.fieldVisibility = FieldVisibility.PRIVATE;
		this.createOptionalGetters = false;
		this.gettersReturnOptional = false;
		this.createSetters = true;
		this.createAllArgsConstructor = true;
		this.enableDecimalLogicalType = false;
		this.suffix = ".java";
		this.stringType = StringType.CharSequence;
		this.maxStringChars = 8192;
		this.templateDir = System.getProperty("com.github.mfds2j.classgen",
				"/org/apache/avro/compiler/specific/templates/java/classic/");
		this.initializeVelocity();
	}

	private void initializeVelocity() {
		this.velocityEngine = new VelocityEngine();
		this.velocityEngine.addProperty("resource.loader", "class, file");
		this.velocityEngine.addProperty("class.resource.loader.class",
				"org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
		this.velocityEngine.addProperty("file.resource.loader.class",
				"org.apache.velocity.runtime.resource.loader.FileResourceLoader");
		this.velocityEngine.addProperty("file.resource.loader.path", "/, .");
		this.velocityEngine.setProperty("runtime.references.strict", true);
		this.velocityEngine.setProperty("space.gobbling", "bc");
	}

	public void initializeContext() {

	}

	public void setTemplateDir(String templateDir) {
		this.templateDir = templateDir;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public boolean isDeprecatedFields() {
		return this.fieldVisibility == FieldVisibility.PUBLIC_DEPRECATED;
	}

	public boolean isPublicFields() {
		return this.fieldVisibility == FieldVisibility.PUBLIC
				|| this.fieldVisibility == FieldVisibility.PUBLIC_DEPRECATED;
	}

	public boolean isPrivateFields() {
		return this.fieldVisibility == FieldVisibility.PRIVATE;
	}
	/**
	 * Sets global field visibility for all attributed fields.
	 * @param fieldVisibility
	 */
	public void setFieldVisibility(FieldVisibility fieldVisibility) {
		this.fieldVisibility = fieldVisibility;
	}

	public boolean isCreateSetters() {
		return this.createSetters;
	}
	
	/**
	 * Sets whether or not setter methods will be generated.
	 * @param createSetters
	 */
	public void setCreateSetters(boolean createSetters) {
		this.createSetters = createSetters;
	}

	public boolean isCreateOptionalGetters() {
		return this.createOptionalGetters;
	}

	public void setCreateOptionalGetters(boolean createOptionalGetters) {
		this.createOptionalGetters = createOptionalGetters;
	}

	/**
	 * Returns whether or not getter methods will return as Optionals wrapping the
	 * original type.
	 * 
	 * @return
	 */
	public boolean isGettersReturnOptional() {
		return this.gettersReturnOptional;
	}

	/**
	 * Sets whether or not the getter methods return Optionals wrapping their return
	 * type or the actual type itself.
	 * 
	 * @param gettersReturnOptional
	 */
	public void setGettersReturnOptional(boolean gettersReturnOptional) {
		this.gettersReturnOptional = gettersReturnOptional;
	}

	/**
	 * Set whether the decimal logical type is enabled.
	 * 
	 * @param enableDecimalLogicalType
	 */
	public void setEnableDecimalLogicalType(boolean enableDecimalLogicalType) {
		this.enableDecimalLogicalType = enableDecimalLogicalType;
	}

	/**
	 * Reflectively invokes a new instance of the parameter class and adds it to the
	 * registry of logical type conversions.
	 * 
	 * @param conversionClass
	 */
	public void addCustomConversion(Class<?> conversionClass) {
		try {
			Conversion<?> conversion = (Conversion<?>) conversionClass.getDeclaredConstructor().newInstance();
			this.virtualData.addLogicalTypeConversion(conversion);
		} catch (InstantiationException | NoSuchMethodException | InvocationTargetException
				| IllegalAccessException var3) {
			throw new RuntimeException("Failed to instantiate conversion class " + conversionClass, var3);
		}
	}

	public enum StringType {
		CharSequence, String, Utf8
	};

	private enum FieldVisibility {
		PUBLIC, PRIVATE, PROTECTED, PACKAGE, PUBLIC_DEPRECATED;
	}
}
