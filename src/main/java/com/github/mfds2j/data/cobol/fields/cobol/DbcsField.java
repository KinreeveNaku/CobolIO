/**
 * 
 */
package com.github.mfds2j.data.cobol.fields.cobol;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import com.github.mfds2j.data.cobol.cb2xml.EnhancedItem;
import com.github.mfds2j.data.cobol.fields.AbstractField;
import com.github.mfds2j.data.cobol.fields.AlphanumericField;

import net.sf.cb2xml.def.Cb2xmlConstants.Usage;

/**
 * @author Andrew
 *
 */
@SuppressWarnings("serial")
public class DbcsField extends AbstractField implements AlphanumericField {

	private final transient Charset charset;
	private final boolean isBigEndian;

	public DbcsField(EnhancedItem properties) {
		super(properties);
		this.charset = StandardCharsets.UTF_16BE;
		this.isBigEndian = true;
	}

	/**
	 * @param properties
	 */
	public DbcsField(EnhancedItem properties, boolean isBigEndian) {
		super(properties);
		this.charset = isBigEndian ? StandardCharsets.UTF_16BE : StandardCharsets.UTF_16LE;
		this.isBigEndian = isBigEndian;
	}

	@Override
	public EnhancedItem getProperties() {
		return super.properties;
	}

	@Override
	public DbcsField newInstance(EnhancedItem properties) {
		return new DbcsField(properties, this.isBigEndian);
	}

	@Override
	public String getTypeAsString() {
		return Usage.DISPLAY_1.getName();
	}

	@Override
	public String getString(byte[] bytes) {
		return new String(bytes, this.charset);
	}

	@Override
	public String getString(byte[] bytes, Charset charset) {
		return new String(bytes, charset);
	}

}
