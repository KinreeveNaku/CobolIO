/**
 * 
 */
package com.ibm.dataaccess.accessor;

import java.nio.charset.Charset;

/**
 * <body> <div> This interface is for restoring data corrupted by charset
 * transformations.</div> <div><b>The contract for this interface requires that
 * instance of any implementation must have an unchanging destination
 * charset.</b> </div>
 * 
 * <pre>
 * e.g...
 * <code>
 * Class A implements Stringable {
 * 	private final Charset c = Charset.forName("CP1047");
 * </code>
 * 	//Compliant : c is not static and is defined on instantiation.
 * <code>
 * 	public static Charset d = Charset.forName("UTF-32");
 * </code>
 * 	//Non-Compliant : d must not be static.
 * <code>
 * }
 * </code>
 * </pre>
 * 
 * </body>
 * 
 * @author Andrew
 *
 */
public interface Stringable {
	@Override
	String toString();

	/**
	 * <body> <div> This method is an data safety mechanism for reverting a String
	 * back to its original data. The charsets provided must match the correct
	 * charsets and exact order in which the original String was corrupted for
	 * integrity to be restored. </div> <div> It is worth noting that this method,
	 * even when successful, may still return a String that is partially corrupted.
	 * This integrity would have been lost when the data was recharacterized
	 * originally. The destination charset should always be either the charset
	 * defined in this accessor's instance or <code>UTF-16BE</code>, but if this is
	 * not possible, this class should be overridden. </div> </body>
	 * 
	 * @param str             The String of data to be recovered.
	 * @param inOrderCharsets The sequence of charsets that the String str needs to
	 *                        be recharacterized through to return to its initial
	 *                        state. This should always be the reverse of the
	 *                        original order of charsets that obfuscated this data.
	 * 
	 * @throws IllegalArgumentException Thrown if the final charset of the parameter
	 *                                  is not the charset stored in this
	 *                                  StringAccessor instance of the charset
	 *                                  <code>UTF-16BE</code>.
	 * 
	 */
	String revert(String str, Charset[] inOrderCharsets);

	/**
	 * @param bytes
	 * @return
	 */
	String convert(byte[] bytes);
}
