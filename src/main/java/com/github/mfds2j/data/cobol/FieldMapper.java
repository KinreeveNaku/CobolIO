/**
 * 
 */
package com.github.mfds2j.data.cobol;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import com.github.mfds2j.Alpha;
import com.github.mfds2j.data.cobol.cb2xml.EnhancedCopybook;
import com.github.mfds2j.data.cobol.cb2xml.EnhancedItem;
import com.github.mfds2j.data.cobol.fields.NamedField;
import com.github.mfds2j.data.cobol.fields.cobol.BinaryField;
import com.github.mfds2j.data.cobol.fields.cobol.HexDoubleField;
import com.github.mfds2j.data.cobol.fields.cobol.HexFloatField;
import com.github.mfds2j.data.cobol.fields.cobol.PackedDecimalField;

import net.sf.cb2xml.def.Cb2xmlConstants.Usage;

/**
 * @author Andrew
 *
 */
public class FieldMapper {
	private final Map<String, Class<? extends NamedField>> fieldMappings;
	private final AtomicInteger calls;
	private final AtomicLong lastCall;
	@Alpha(major = 0, minor = 0, snapshot = 5)
	private Map<Class<? extends NamedField>, NamedField> fieldClassMappings;// @TODO Ideal to use this storage scheme
																			// as opposed to strings which could have
																			// the same name. may require some form of
																			// descriptor for Item to Class
																			// relationships.

	public FieldMapper() {
		calls = new AtomicInteger();
		lastCall = new AtomicLong(System.nanoTime());
		fieldMappings = new ConcurrentHashMap<>();
	}

	public void introspectFields(EnhancedCopybook cpy) {
		cpy.getItems().forEach(this::introspectItem);
	}

	private void introspectItem(EnhancedItem enhancedItem) {
		if (enhancedItem.getItems() != null && !enhancedItem.getItems().isEmpty()) {
			enhancedItem.getItems().forEach(this::introspectItem);
		} else {
			resolve(enhancedItem);
		}
	}

	/**
	 * @param enhancedItem
	 */
	private void resolve(EnhancedItem enhancedItem) {
		if (Boolean.TRUE.equals(enhancedItem.isNumeric())) {// Numeric
			if (Boolean.TRUE.equals(enhancedItem.isEdittedNumeric())) {// EditedNumeric
				switch (Usage.valueOf(enhancedItem.getUsage())) {
				case NONE:// Defaults to Display
				case DISPLAY:// PlainText
					break;
				case COMP_1:// SingleFloatingPoint
					enhancedItem.setField(
							fieldClassMappings.get(fieldMappings.getOrDefault(enhancedItem.getUsage(), HexFloatField.class)));
					break;
				case COMP_2:// DoubleFloatingPoint
					enhancedItem.setField(
							fieldClassMappings.get(fieldMappings.getOrDefault(enhancedItem.getUsage(), HexDoubleField.class)));
					break;
				case COMP_3:// PackedDecimal
				case PACKED_DECIMAL:// SignedPackedDecimal
					enhancedItem.setField(fieldClassMappings
							.get(fieldMappings.getOrDefault(enhancedItem.getUsage(), PackedDecimalField.class)));
					break;
				case COMP_6:// UnsignedPackedDecimal
				case COMP:// BinaryTwosCompliment OR SingleFloatingPoint, depending on CompilerOptions.
							// May require partial redesign later
				case BINARY:// BinaryTwosCompliment
				case COMP_4:// BinaryTwosCompliment
					enhancedItem.setField(
							fieldClassMappings.get(fieldMappings.getOrDefault(enhancedItem.getUsage(), BinaryField.class)));
					break;
				case COMP_5:// Implementation specific BinaryTwosCompliment
					enhancedItem.setField(
							fieldClassMappings.get(fieldMappings.getOrDefault(enhancedItem.getUsage(), BinaryField.class)));
					break;
				case NATIONAL:// Two byte length encoded plain text
					break;
				case DISPLAY_1:// DBCS PlainText
					break;
				case INDEX:
					// TODO Recall what this is and determine if this can be handled.
					break;
				case OBJECT_REFERENCE:
					throw new UnsupportedOperationException("Object references are not currently supported.");
				case POINTER:
				case FUNCTION_POINTER:
				case PROCEDURAL_POINTER:
					throw new UnsupportedOperationException("Pointers cannot be resolved.");
				default:
					throw new IllegalArgumentException();

				}
			} else {// Non-EditedNumeric

			}
		} else {// Non-Numeric

		}

	}

	public NamedField getMappingForFieldDescription(EnhancedItem properties) {
		return properties.getField();
	}

	@Alpha(major = 0, minor = 0, snapshot = 5)
	public NamedField put(Class<NamedField> key, NamedField value) {
		return fieldClassMappings.put(key, value);
	}

	public NamedField put(String key, NamedField value) {
		fieldMappings.put(key, value.getClass());
		return fieldClassMappings.put(value.getClass(), value);

	}

	/**
	 * Returns a Set of names of all registered field types for this mapping.
	 * 
	 * @return An unmodifiable Set of String values representing all registered
	 *         types.
	 */
	public final Set<String> getFieldTypes() {
		this.calls.incrementAndGet();
		this.lastCall.getAndSet(System.nanoTime());
		return Collections.unmodifiableSet(fieldMappings.keySet());
	}
	
	public synchronized long getLastCall() {
		return this.lastCall.getAndSet(System.nanoTime());
	}
}
