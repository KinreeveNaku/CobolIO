/**
 * 
 */
package com.github.mfds2j.classgen;

import java.util.Properties;

/**
 * @author Andrew
 *
 */
public class MFDS2JProperties extends Properties {
	
	private Properties velocityProperties;

	private boolean isGenGetters;

	private boolean isGenSetters;

	private String typeConverter;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public MFDS2JProperties() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param defaults
	 */
	public MFDS2JProperties(Properties defaults) {
		super(defaults);
		//this.velocityProperties = Collectors.groupingBy();//TODO
	}
	
	public boolean isGenerateGetters() {
		return isGenGetters;
	}

	public boolean isGenerateSetters() {
		return isGenSetters;
	}
	
	public Class<?> getTypeConverter() {
		return null;
		
	}
}
