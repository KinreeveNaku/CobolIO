/**
 * 
 */
package com.ibm.dataaccess.accessor;

import org.springframework.core.convert.converter.Converter;

/**
 * http://www.iro.umontreal.ca/~keller/Layla/translator.pdf
 * @author Andrew
 *
 */
public interface Accessor<T> extends Converter<byte[], T> {
	@Override
	T convert(byte[] bytes);
}
