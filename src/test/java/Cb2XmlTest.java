import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import net.sf.cb2xml.Cb2Xml;

/**
 * 
 */

/**
 * @author Andrew
 *
 */
public class Cb2XmlTest {
	public static void main(String[] args) throws FileNotFoundException {
		File f = new File("./src/test/resources/CobolExample.cbl");
		InputStream st = new FileInputStream(f);
		String xml = Cb2Xml.convertToXMLString(st, "MASTER_REC", true);
		System.out.println(xml);
	}
}
