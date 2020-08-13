/**
 * 
 */
package com.github.mfds2j.data.cobol;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import com.github.mfds2j.data.cobol.cb2xml.EnhancedCopybook;
import com.github.mfds2j.data.cobol.cb2xml.EnhancedItem;
import com.github.mfds2j.data.cobol.fields.NumericField;

import net.sf.cb2xml.def.Cb2xmlConstants.Usage;

/**
 * @author Andrew
 *
 */
public class FixedBinaryReader extends AbstractBinaryReader {

	private final EnhancedCopybook book;
	private final FieldMapper resolver;

	public FixedBinaryReader(EnhancedCopybook book, FieldMapper resolver, BinaryStream stream) {
		super(stream);
		this.book = book;
		this.resolver = resolver;
	}

	@Override
	public String readRecord() throws IOException {
		StringBuilder builder = new StringBuilder();
		byte[] recordBytes = new byte[book.getLength()];
		int len = super.stream.read(recordBytes);
		if (len != book.getLength()) {
			throw new IOException(String.format(
					"The bytes read did not equal the requested length. The number of bytes expected was %s while only %s were read.",
					book.getLength(), len));
		} else {
			for (EnhancedItem fData : book.getItems()) {
				if (Boolean.TRUE.equals(fData.isNumeric())) {
					builder.append(((NumericField) fData.getField())
							.asString(Arrays.copyOfRange(recordBytes, fData.getPosition(), fData.getPosition() + fData.getStorageLength())));
				} else if(Usage.NATIONAL.getName().equals(fData.getUsage())) {
					builder.append(new String(Arrays.copyOfRange(recordBytes, fData.getPosition(), fData.getPosition() + fData.getStorageLength()), StandardCharsets.UTF_16));
				} else {
					//Treat as Display
					builder.append(new String(Arrays.copyOfRange(recordBytes, fData.getPosition(), fData.getPosition() + fData.getStorageLength()), Charset.defaultCharset()));//Needs validation testing.
				}

			}
		}
		return builder.toString();
	}

	@Override
	protected EnhancedCopybook get(Integer length) {
		if (book.getLength() == length) {
			return book;
		} else {
			throw new IllegalArgumentException(
					"The length specified does not match the copybook assigned to this reader.");
		}
	}

}
