/**
 * <body> <div> This package contains various classes which, together, create a
 * framework for supporting the conversion of the contents of a copybook in
 * unprocessed binary format into various formats, including Avro, plain text,
 * JSON, and xml. </div> </body>
 * 
 * <h3>Regex to add constructor to converter class</h3>
 * 
 * <div>
 * <h2>Look for</h2>
 * <code>(public class)\ ([A-Za-z]*)\ (implements [A-Za-z]* \{) $1 $2 $3\r\n\r\n\tprivate Item item;\r\n\r\n\t</code>
 * <h2>Replace with</h2>
 * <code>public $2(Item item) {\r\n\t\tthis.item = item;\r\n\t}\r\n</code></div>
 */

package com.ibm;
@interface DataAccess{}
@interface RecordGenerator{}
@interface Cb2Xml{}