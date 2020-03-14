/**
 * 
 */
package com.github.mfds2j.classgen;

import java.util.List;

import org.apache.velocity.VelocityContext;

import com.github.mfds2j.data.cobol.cb2xml.EnhancedCopybook;
import com.github.mfds2j.data.cobol.cb2xml.EnhancedItem;


/**
 * @author Andrew
 *
 */
public class ContextGenerator {
	private static final String FIELD_NAME_REGEX = "^\\${0,1}[a-z][a-zA-Z0-9]*$";
	public static final String NAME_SPACE = "namespace";
	public static final String IMPORTS = "imports";
	public static final String IMPORT = "import";
	public static final String CANON_CLASS_NAME = "canonClassName";
	public static final String CLASS_NAME = "className";

	public static final String PROPERTIES = "properties";
	public static final String PROPERTY = "property";
	public static final String FIELD_TYPE = "fieldType";
	public static final String FIELD_NAME = "fieldName";
	public static final String FIELD_VALUE = "fieldValue";
	public static final String FIELD_INDEX = "fieldIndex";
	/*
	 * Will there be multiple contexts if there are multiple classes.
	 */
	private final VelocityContext context;
	private final EnhancedCopybook book;
	private String rootNamespace;

	/**
	 * 
	 */
	public ContextGenerator(EnhancedCopybook book, String namespace) {
		this.book = book;
		this.context = new VelocityContext();
		this.rootNamespace = namespace;
	}

	public void Init() {
		context.put(NAME_SPACE, rootNamespace);
		List<EnhancedItem> items = this.book.getItems();
		//Ignore first level item. It is always the copybook root level. Or should it be treated as the copybook itself?
		FieldProperties properties = new FieldProperties();
		for(EnhancedItem item : items) {
			getProperties(item, properties);
		}
	}

	private void getProperties(EnhancedItem item, FieldProperties result) {
		//for(EnhancedItem child : item.getItems()) {
			//if(child.getItems() == null || child.getItems().isEmpty()) {
				if(item.getOccurs() > 1) {
					FieldProperty property = new FieldProperty(item.getName(), resolveType(item.getPicture()), result.size() - 1, item.getValue(), item.getName().substring(0,1).toUpperCase().concat(item.getName().substring(1)));
					property.setIsArray(true);
					result.add(property);
				}
			//} else {
				//getProperties(child, result);
			//}
		//}
	}

	/**
	 * @param picture
	 * @return
	 */
	private String resolveType(String picture) {
		// TODO Auto-generated method stub
		return null;
	}

	private boolean validateField(FieldProperty property) {
		return (property.getFieldName().matches(FIELD_NAME_REGEX)) && (property.getFieldIndex() >= 0)
				&& (property.getFieldType().contains("."));
	}
}
