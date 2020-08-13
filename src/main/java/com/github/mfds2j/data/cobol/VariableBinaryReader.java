/**
 * 
 */
package com.github.mfds2j.data.cobol;

import java.util.Map;
import java.util.NoSuchElementException;

import com.github.mfds2j.data.cobol.cb2xml.EnhancedCopybook;

/**
 * @author Andrew
 *
 */
public class VariableBinaryReader extends AbstractBinaryReader {
	private Map<Integer, EnhancedCopybook> books;
	
	/**
	 * @param books
	 * @param stream
	 */
	public VariableBinaryReader(Map<Integer, EnhancedCopybook> books, BinaryStream stream) {
		super(stream);
		this.books = books;
	}

	@Override
	public String readRecord() {
		// TODO Write up logic
		return null;
	}
	
	@Override
	protected EnhancedCopybook get(Integer length) {
		EnhancedCopybook book = books.get(length);
		if (book == null) {
			throw new NoSuchElementException("There exists no copybook with a length of " + length);
		}
		return book;
	}
}
