import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.bind.JAXB;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.OrderWith;
import org.junit.runner.manipulation.Alphanumeric;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.cobolio.internal.xml.Cb2Xml2Velocity;
import com.github.cobolio.internal.xml.cb2xml.Copybook;

import net.sf.cb2xml.Cb2Xml;

/**
 * 
 */

/**
 * @author Andrew
 *
 */
@OrderWith(Alphanumeric.class)
public class Cb2Xml2JsonTest {
	private static final Class<Copybook> COPY_CLASS = Copybook.class;
	private static final String RESOURCES_PATH = "./src/test/resources/";
	private static final String FILE_NAME_STRING = "CobolExample";
	private static final String COBOL_EXT_STRING = ".cbl";
	private static final String XML_EXT_STRING = ".xml";
	private static final String JSON_EXT_STRING = ".json";
	private static Copybook Control;
	private static ObjectMapper mapper;
	private File xmlFile;
	private File jsonFile;
	private File cobolFile;
	private File testOut;
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		mapper = new ObjectMapper();
		Control = JAXB.unmarshal(new ByteArrayInputStream(XML_TEST_STRING.getBytes(UTF_8)), COPY_CLASS);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		cobolFile = new File(RESOURCES_PATH + "CobolExample.cbl");
		xmlFile = new File(RESOURCES_PATH + "CobolExample.xml");
		jsonFile = new File(RESOURCES_PATH + "CobolExample.json");
		testOut = new File(RESOURCES_PATH + "testOutput");
		testOut.createNewFile();
	}

	@Test
	public final void test2_Cb2Xml() {
		try {
			Cb2Xml.convertToXMLString(cobolFile);
		} catch (Exception e) {
			e.printStackTrace();
			fail("Cb2Xml failed to perform the transformation.");
		}
	}
	
	/**
	 * Test method for
	 * {@link com.github.cobolio.internal.xml.Cb2Xml2Velocity#unmarshal(java.io.File)}.
	 */
	@Test
	public final void test3_UnmarshalFile() {
		Copybook cpy = Cb2Xml2Velocity.unmarshal(xmlFile);
		checkForFail(cpy);
	}

	/**
	 * Test method for
	 * {@link com.github.cobolio.internal.xml.Cb2Xml2Velocity#unmarshal(java.lang.String)}.
	 */
	@Test
	public final void test4_UnmarshalString() {
		Copybook cpy = Cb2Xml2Velocity.unmarshal(XML_TEST_STRING);
		checkForFail(cpy);
	}

	/**
	 * Test method for
	 * {@link com.github.cobolio.internal.xml.Cb2Xml2Velocity#unmarshal(java.io.InputStream)}.
	 */
	@Test
	public final void test5_UnmarshalInputStream() {
		try {
			InputStream is = new FileInputStream(xmlFile);
			Copybook cpy = Cb2Xml2Velocity.unmarshal(is);
			checkForFail(cpy);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			fail("Unmarshalling failed. File not found.");
		}
		
	}

	/**
	 * Test method for
	 * {@link com.github.cobolio.internal.xml.Cb2Xml2Velocity#marshal(com.github.cobolio.internal.xml.cb2xml.Copybook, java.io.File)}.
	 */
	@Test
	public final void test6_MarshalCopybookFile() {
		try {
			Cb2Xml2Velocity.marshal(Control, testOut);
		} catch (Exception e) {
			e.printStackTrace();
			fail("Marshalling failed.");
		}
	}

	/**
	 * Test method for
	 * {@link com.github.cobolio.internal.xml.Cb2Xml2Velocity#marshal(com.github.cobolio.internal.xml.cb2xml.Copybook, java.io.OutputStream)}.
	 */
	@Test
	public final void test7_MarshalCopybookOutputStream() {
		try {
			Cb2Xml2Velocity.marshal(Control, new FileOutputStream(testOut));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			fail("File not found.");
		} catch (Exception e) {
			e.printStackTrace();
			fail("Marshalling failed.");
		}
		
	}

	/**
	 * Test method for
	 * {@link com.github.cobolio.internal.xml.Cb2Xml2Velocity#marshal(com.github.cobolio.internal.xml.cb2xml.Copybook, java.lang.String)}.
	 */
	@Test
	public final void test8_MarshalCopybookString() {
		try {
			Cb2Xml2Velocity.marshal(Control, testOut.getPath());
		} catch (Exception e) {
			e.printStackTrace();
			fail("Marshalling failed");
		}
	}

	public static void main(String[] args) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		File f = new File("./src/test/resources/CobolExample.xml");
		File o = new File("./src/test/resources/CobolExample.json");
		Copybook cpy = JAXB.unmarshal(f, Copybook.class);
		mapper.writeValue(o, cpy);

	}
	
	private static final void checkForFail(Copybook cpy) {
		if(cpy == null || !Control.equals(cpy)) {//thorough equals checking
			fail("Unmarshalling failed. Copybook generated does not match the control object.");
		}
	}
	
	private static final String XML_TEST_STRING =
			"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" + 
			"<copybook filename=\"MASTER_REC\" dialect=\"Mainframe\">\r\n" + 
			"	<item level=\"01\" name=\"MASTER_REC\" position=\"1\"\r\n" + 
			"		storage-length=\"37\" display-length=\"43\">\r\n" + 
			"		<item level=\"05\" name=\"ACCOUNT_NO\" picture=\"X(9)\" position=\"1\"\r\n" + 
			"			storage-length=\"9\" display-length=\"9\" />\r\n" + 
			"		<item level=\"05\" name=\"REC_TYPE\" picture=\"X\" position=\"10\"\r\n" + 
			"			storage-length=\"1\" display-length=\"1\" />\r\n" + 
			"		<item level=\"05\" name=\"AMOUNT\" picture=\"S9(4)V99\"\r\n" + 
			"			usage=\"computational-3\" position=\"11\" storage-length=\"4\"\r\n" + 
			"			display-length=\"6\" scale=\"2\" numeric=\"true\" signed=\"true\" />\r\n" + 
			"		<item level=\"05\" name=\"BIN-NO\" picture=\"S9(8)\"\r\n" + 
			"			usage=\"computational\" position=\"15\" storage-length=\"4\"\r\n" + 
			"			display-length=\"8\" numeric=\"true\" redefined=\"true\" signed=\"true\" />\r\n" + 
			"		<item level=\"05\" name=\"BIN-NO-X\" picture=\"XXXX\" position=\"15\"\r\n" + 
			"			storage-length=\"4\" display-length=\"4\" redefines=\"BIN-NO\" />\r\n" + 
			"		<item level=\"05\" name=\"DECIMAL-NO\" picture=\"S999\" position=\"19\"\r\n" + 
			"			storage-length=\"3\" display-length=\"3\" numeric=\"true\" signed=\"true\" />\r\n" + 
			"		<item level=\"05\" name=\"MASTER-DATE\" position=\"22\"\r\n" + 
			"			storage-length=\"6\" display-length=\"6\" redefined=\"true\">\r\n" + 
			"			<item level=\"10\" name=\"DATE-YY\" picture=\"9(2)\" position=\"22\"\r\n" + 
			"				storage-length=\"2\" display-length=\"2\" numeric=\"true\" />\r\n" + 
			"			<item level=\"10\" name=\"DATE-MM\" picture=\"9(2)\" position=\"24\"\r\n" + 
			"				storage-length=\"2\" display-length=\"2\" numeric=\"true\" />\r\n" + 
			"			<item level=\"10\" name=\"DATE-DD\" picture=\"9(2)\" position=\"26\"\r\n" + 
			"				storage-length=\"2\" display-length=\"2\" numeric=\"true\" />\r\n" + 
			"		</item>\r\n" + 
			"		<item level=\"05\" name=\"MASTER-DOB\" position=\"22\"\r\n" + 
			"			storage-length=\"6\" display-length=\"6\" redefines=\"MASTER-DATE\">\r\n" + 
			"			<item level=\"10\" name=\"YYMMDD\" picture=\"XXXXXX\" position=\"22\"\r\n" + 
			"				storage-length=\"6\" display-length=\"6\" />\r\n" + 
			"		</item>\r\n" + 
			"		<item level=\"05\" name=\"ACT_TYPE\" picture=\"X\" position=\"28\"\r\n" + 
			"			storage-length=\"1\" display-length=\"1\" />\r\n" + 
			"		<item level=\"05\" name=\"OTHER-DATE\" position=\"29\"\r\n" + 
			"			storage-length=\"6\" display-length=\"6\" redefined=\"true\">\r\n" + 
			"			<item level=\"10\" name=\"ODATE-YY\" picture=\"9(2)\" position=\"29\"\r\n" + 
			"				storage-length=\"2\" display-length=\"2\" numeric=\"true\" />\r\n" + 
			"			<item level=\"10\" name=\"ODATE-MM\" picture=\"9(2)\" position=\"31\"\r\n" + 
			"				storage-length=\"2\" display-length=\"2\" numeric=\"true\" />\r\n" + 
			"			<item level=\"10\" name=\"ODATE-DD\" picture=\"9(2)\" position=\"33\"\r\n" + 
			"				storage-length=\"2\" display-length=\"2\" numeric=\"true\" />\r\n" + 
			"		</item>\r\n" + 
			"		<item level=\"05\" name=\"OTHER-DOB\" position=\"29\"\r\n" + 
			"			storage-length=\"8\" display-length=\"8\" redefines=\"OTHER-DATE\">\r\n" + 
			"			<item level=\"10\" name=\"OYYMMDDTT\" picture=\"9(8)\" position=\"29\"\r\n" + 
			"				storage-length=\"8\" display-length=\"8\" numeric=\"true\" />\r\n" + 
			"		</item>\r\n" + 
			"		<item level=\"05\" name=\"OTHER_TYPE\" picture=\"X\" position=\"37\"\r\n" + 
			"			storage-length=\"1\" display-length=\"1\" />\r\n" + 
			"	</item>\r\n" + 
			"	<item level=\"01\" name=\"OTHER_REC\" picture=\"X\" position=\"38\"\r\n" + 
			"		storage-length=\"1\" display-length=\"1\"/>\r\n" + 
			"</copybook>";
}
