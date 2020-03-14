/**
 * 
 */
package com.ibm.analysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import com.ibm.dataaccess.accessor.AbstractCobolAccessor;
import com.ibm.dataaccess.accessor.impl.AccessorStore;

import net.sf.cobol2j.Cobol2JException;
import net.sf.cobol2j.FieldFormat;
import net.sf.cobol2j.FieldsGroup;
import net.sf.cobol2j.FieldsList;
import net.sf.cobol2j.FileFormat;
import net.sf.cobol2j.RecordFormat;
import net.sf.cobol2j.RecordTemplateContainer;

/**
 * @author Andrew
 *
 */
public class XmlTemplatePreprocessor {

	private FileFormat file;
	private AccessorStore store;

	public XmlTemplatePreprocessor(FileFormat file) {
		this.file = file;
	}

	public Map<String, RecordTemplateContainer> loadRecordMap(FileFormat file) {
		Map<String, RecordTemplateContainer> recordMap = new HashMap<>();
		for (RecordFormat format : file.getRecordFormat()) {
			recordMap.put(format.getCobolRecordName(), getTemplate(format));
		}
		return recordMap;
	}

	public List<RecordFormat> getRecordTemplates() {
		return this.file.getRecordFormat();
	}

	public RecordTemplateContainer getTemplate(RecordFormat format) {
		return loadTemplate(format, this.store);

	}

	public RecordTemplateContainer getTemplate(String recordName) {
		RecordFormat record = null;
		for (RecordFormat current : getRecordTemplates()) {
			if (current.getCobolRecordName().equals(recordName)) {
				record = current;
				break;
			}
		}
		if (record == null) {
			throw new NoSuchElementException("A RecordFormat was not found with the name ".concat(recordName));
		}
		return loadTemplate(record, this.store);
	}

	public static RecordTemplateContainer loadTemplate(RecordFormat record, AccessorStore store) {
		List<String> fieldNames = new ArrayList<>();
		List<FieldFormat> formats = new ArrayList<>();
		try {
			getFields(record, fieldNames, formats, 0);
			return new RecordTemplateContainer(record, fieldNames, formats, resolveAccessors(formats, store));
		} catch (NullPointerException | ArrayIndexOutOfBoundsException e) {
			throw new Cobol2JException("A loading issue occurred in cobol2j", e);
		}

	}

	private static void getFields(FieldsList group, List<String> fieldNames, List<FieldFormat> formats,
			int occursIter) {
		for (Object o : group.getFieldFormatOrFieldsGroup()) {
			if (o instanceof FieldsGroup) {
				addFieldFormats((FieldsGroup) o, fieldNames, formats);

			} else if (o instanceof FieldFormat) {
				addFieldFormat((FieldFormat) o, fieldNames, formats, occursIter);
			}
		}
	}

	private static void addFieldFormats(FieldsGroup o, List<String> fieldNames, List<FieldFormat> formats) {
		if (o.getOccurs().intValueExact() > 1) {
			for (int i = 0; i < o.getOccurs().intValueExact(); i++) {
				getFields(((FieldsGroup) o), fieldNames, formats, i);
			}
		} else {
			getFields(o, fieldNames, formats, 0);
		}
	}

	private static void addFieldFormat(FieldFormat o, List<String> fieldNames, List<FieldFormat> formats,
			int occursIter) {

		if (o.getOccurs().intValueExact() > 1) {
			for (int i = 0; i < o.getOccurs().intValueExact(); i++) {
				formats.add(o);
				fieldNames.add(o.getName() + i);
			}
		} else if (occursIter != 0) {
			formats.add(o);
			fieldNames.add(o.getName() + occursIter);
		} else {
			formats.add((FieldFormat) o);
			fieldNames.add(o.getName());
		}

	}

	private static Map<String, AbstractCobolAccessor> resolveAccessors(List<FieldFormat> formats, AccessorStore store) {
		Map<String, AbstractCobolAccessor> accessors = new LinkedHashMap<>();
		for (FieldFormat format : formats) {
			accessors.put(format.getName(), store.getAccessorForFieldFormat(format));
		}
		return accessors;
	}
}
