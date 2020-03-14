/**
 * 
 */
package com.github.mfds2j.classgen;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Andrew
 *
 */
public final class FieldProperties extends ArrayList<FieldProperty> {

	@Override
	public boolean remove(Object o) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void clear() {
		throw new UnsupportedOperationException();

	}

	@Override
	public FieldProperty set(int index, FieldProperty element) {
		throw new UnsupportedOperationException();
	}

	@Override
	public FieldProperty remove(int index) {
		throw new UnsupportedOperationException();
	}
	
	public boolean add(String fieldName, String fieldType, String fieldValue, int fieldIndex, String fieldGetSetName) {
		return this.add(new FieldProperty(fieldName, fieldType, fieldIndex, fieldValue, fieldGetSetName));
	}

}
