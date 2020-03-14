/**
 * 
 */
package net.sf.cobol2j;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import com.ibm.dataaccess.accessor.AbstractCobolAccessor;

/**
 * @author Andrew
 *
 */
public class RecordTemplateContainer {
	/**
	 * 
	 */
	private static final Pattern B12369 = Pattern.compile("[B12369]");
	private RecordFormat record;
	private Map<String, AbstractCobolAccessor> converters;
	private List<String> fieldNames;
	private List<FieldFormat> formats;

	public RecordTemplateContainer() {
		this.converters = new LinkedHashMap<>();
		this.fieldNames = new ArrayList<>();
		this.formats = new ArrayList<>();
	}

	/**
	 * @param record
	 * @param fieldNames
	 * @param formats
	 */
	public RecordTemplateContainer(RecordFormat record, List<String> fieldNames, List<FieldFormat> formats,
			Map<String, AbstractCobolAccessor> accessors) {
		this.record = record;
		this.fieldNames = fieldNames;
		this.formats = formats;
		this.converters = accessors;
	}

	public String getFieldName(int index) {
		return this.fieldNames.get(index);
	}

	public FieldFormat getFieldFormat(int index) {
		return this.converters.get(fieldNames.get(index)).getFormat();
	}
}
