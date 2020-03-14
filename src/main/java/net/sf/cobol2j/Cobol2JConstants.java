/**
 * 
 */
package net.sf.cobol2j;

import net.sf.cb2xml.analysis.Item;

/**
 * <pre>
 * 	&lt;xsl:choose&gt;
 * 		&lt;xsl:when test="@numeric='true' or substring(@usage, 1, 4)='comp'"&gt;
 * 			&lt;xsl:attribute name="Decimal"&gt;
 * 				&lt;xsl:choose&gt;
 * 					&lt;xsl:when test="@scale"&gt;
 * 						&lt;xsl:value-of select="@scale"/&gt;
 * 					&lt;/xsl:when&gt;
 * 					&lt;xsl:otherwise&gt;0&lt;/xsl:otherwise&gt;
 * 				&lt;/xsl:choose&gt;
 * 			&lt;/xsl:attribute&gt;
 * 			&lt;xsl:choose&gt;
 * 				&lt;xsl:when test="@usage='binary' or @usage='computational' or @usage='computational-4' or @usage='computational-5'"&gt;
 * 					&lt;xsl:attribute name="Type"&gt;B&lt;/xsl:attribute&gt;
 * 					&lt;xsl:attribute name="Size"&gt;
 * 						&lt;xsl:choose&gt;
 * 							&lt;xsl:when test="@display-length &lt; 5"&gt;2&lt;/xsl:when&gt;
 * 							&lt;xsl:when test="@display-length &lt; 10"&gt;4&lt;/xsl:when&gt;
 * 							&lt;xsl:otherwise&gt;8&lt;/xsl:otherwise&gt;
 * 						&lt;/xsl:choose&gt;
 * 					&lt;/xsl:attribute&gt;
 * 				&lt;/xsl:when&gt;
 * 				&lt;xsl:when test="@usage='computational-1'"&gt;
 * 					&lt;xsl:attribute name="Type"&gt;1&lt;/xsl:attribute&gt;
 * 					&lt;xsl:attribute name="Size"&gt;4&lt;/xsl:attribute&gt;
 * 				&lt;/xsl:when&gt;
 * 				&lt;xsl:when test="@usage='computational-2'"&gt;
 * 					&lt;xsl:attribute name="Type"&gt;2&lt;/xsl:attribute&gt;
 * 					&lt;xsl:attribute name="Size"&gt;8&lt;/xsl:attribute&gt;
 * 				&lt;/xsl:when&gt;
 * 				&lt;xsl:when test="@usage='computational-3' or @usage='packed-decimal'"&gt;
 * 					&lt;xsl:attribute name="Type"&gt;3&lt;/xsl:attribute&gt;
 * 				&lt;/xsl:when&gt;
 * 				&lt;xsl:when test="contains(@picture, 'COMP-6')"&gt;
 * 					&lt;xsl:attribute name="Type"&gt;6&lt;/xsl:attribute&gt;
 * 				&lt;/xsl:when&gt;
 * 				&lt;xsl:otherwise&gt;
 * 					&lt;xsl:attribute name="Type"&gt;9&lt;/xsl:attribute&gt;
 * 				&lt;/xsl:otherwise&gt;
 * 			&lt;/xsl:choose&gt;
 * 		&lt;/xsl:when&gt;
 * 		&lt;xsl:otherwise&gt;
 * 			&lt;xsl:attribute name="Type"&gt;X&lt;/xsl:attribute&gt;
 * 			&lt;xsl:attribute name="Decimal"&gt;0&lt;/xsl:attribute&gt;
 * 		&lt;/xsl:otherwise&gt;
 * 	&lt;/xsl:choose&gt;
 * </pre>
 * 
 * @author Andrew
 *
 */
public class Cobol2JConstants {

	public static String getTypeFromItem(Item item) {
		if (item.getPicture().contains("9") && !item.getPicture().contains("X")) {
			switch (item.getUsage()) {
			case BINARY:// continue
			case COMP:// continue
			case COMP_4:// continue
			case COMP_5:// continue
				return "B";// Type
			// if display-length < 5 then Size = 2 byte, else if display-length < 10 then
			// Size = 4 byte, else Size = 8 byte.
			case COMP_1:
				return "1";// Type IBM Single Precision Hex Float
			// Size = 4 byte
			case COMP_2:
				return "2";// Type IBM Double Precision Hex Float
			// Size = 8 byte
			case COMP_3:// continue
			case PACKED_DECIMAL:
				return "3";// Type Signed Packed Decimal
			//
			case COMP_6:
				if (item.getPicture() != null) {
					return "6";// Type Unsigned Packed Decimal
				} else {
					return "9";// Leads to default
				}
			default:
				return "9";// Type Display Numeric
			}
		} else {
			return "X";
		}
	}
}
