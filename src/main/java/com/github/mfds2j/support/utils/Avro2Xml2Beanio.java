/**
 * 
 */
package com.github.mfds2j.support.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class provides functionality for converting avro json into beanio xml.
 * This class relies on nothing but the avro schema itself to do this, so
 * nothing else needs to be provided other than file paths and switches.
 * 
 * Because of the lack of additional information as well as limitations of the
 * current design, certain aspects such as field lengths must be provided in
 * post.
 * 
 * Additionally, this does not support enumerations as of yet.
 * This class is also currently designed to only support fixed length record types
 * for beanio.
 * <b>DO NOT EDIT</b>
 * 
 * Oh and don't forget to change the stream names from 'CHANGE_ME'!
 * They are hardcoded to that on generation.
 * @author Andrew
 *
 */
public class Avro2Xml2Beanio {
	/**
	 * Runtime return status indicating a SecurityException was thrown.
	 */
	public static final int ACCESS_SECURITY_EXECPTION = 4;
	/**
	 * Runtime return status indicating a NPE was thrown.
	 */
	public static final int NULL_POINTER_EXCEPTION = 3;
	private static final String GETTERS_FLAG_STRING = "-getters";
	private static final String SETTERS_FLAG_STRING = "-setters";
	private static final String TRANSLET_PATH_STRING = "./src/main/java/com/github/mfds2j/support/utils/avro2beanio.xsl";
	private static final String XML_OUT_PATH_STRING = "./store.xml";
	private static final String USAGE_STRING = "usage\r\n-avro\r\n\tThe absolute or relative path to the avro schema to be parsed.\r\n-xml (optional)\r\n\tAn optional output path for the xml transformation of the avro schema.\r\n-output\r\n\tThe output path for the resulting BeanIO XML file.\r\n-indent\r\n\tAn optional valuefor if the output xml should be indented.(Defaults to yes) Values are (yes|no).\r\n e.g.:\r\n\t-avro ./avro.avsc -xml ./transformed.xml -output beanio.xml";
	private static final String AVRO_FLAG_STRING = "-avro";
	private static final String XML_FLAG_STRING = "-xml";
	private static final String OUTPUT_FLAG_STRING = "-output";
	
	private static final String AVRO_STRING = "avro";
	private static final String XML_STRING = "xml";
	private static final String GETTERS_STRING = "getters";
	private static final String OUTPUT_STRING = "output";
	private static final String SETTERS_STRING = "setters";
	
	public Avro2Xml2Beanio() {
		
	}
	@SuppressWarnings({"java:S1166", "java:S1148", "java:S134", "java:S126", "java:S106"})
	public static void main(String[] args) throws FileNotFoundException {
		String avroPath = null;
		String xmlPath = null;
		String outPath = null;
		File output = null;
		
		Map<String, Object> userArguments = new HashMap<>();
		
		try {
			List<String> configArgs = Arrays.asList(args);
			for(int i = 0; i < configArgs.size(); i++) {
				if(AVRO_FLAG_STRING.equals(configArgs.get(i))) {
					if(configArgs.size() >= i + 1) {
						userArguments.put(AVRO_STRING, configArgs.get(i + 1));
					}
				} else if(XML_FLAG_STRING.equals(configArgs.get(i))) {
					if(configArgs.size() >= i + 1) {
						userArguments.put(XML_STRING, configArgs.get(i + 1));
					}
				} else if(OUTPUT_FLAG_STRING.equals(configArgs.get(i))) {
					if(configArgs.size() >= i + 1) {
						userArguments.put(OUTPUT_STRING, configArgs.get(i + 1));
					}
				} else if(GETTERS_FLAG_STRING.equals(configArgs.get(i))) {
					if(configArgs.size() >= i + 1) {
						userArguments.put(GETTERS_STRING, configArgs.get(i + 1));
					}
				} else if(SETTERS_FLAG_STRING.equals(configArgs.get(i))) {
					if(configArgs.size() >= i + 1) {
						userArguments.put(SETTERS_STRING, configArgs.get(i + 1));
					}
				} else if(("-" + javax.xml.transform.OutputKeys.ENCODING).equals(configArgs.get(i))) {
					if(configArgs.size() >= i + 1) {
						userArguments.put(javax.xml.transform.OutputKeys.ENCODING, configArgs.get(i + 1));
					}
				} else if(("-" + javax.xml.transform.OutputKeys.INDENT).equals(configArgs.get(i))) {
					if(configArgs.size() >= i + 1) {
						userArguments.put(javax.xml.transform.OutputKeys.INDENT, configArgs.get(i + 1));
					}
				} else {
					
				}
					
			}
			avroPath = (String) userArguments.get(AVRO_STRING);
			xmlPath = (String) userArguments.getOrDefault(XML_STRING, XML_OUT_PATH_STRING);
			outPath = (String) userArguments.get(OUTPUT_STRING);
			if(avroPath == null) {
				throw new NullPointerException("No value was provided for the input file(-avro).");
			} else if(outPath == null) {
				throw new NullPointerException("No value was provided for the output file(-output).");
			}
			output = new File(outPath);
			if(!output.getParentFile().exists()) {
				Files.createDirectories(output.getParentFile().toPath());
			}
			if(!output.exists() && !output.createNewFile()) {
				//throw new SecurityException("The file could not be created due to insufficient permissions or access.");
				Runtime.getRuntime().exit(ACCESS_SECURITY_EXECPTION);
			}
		} catch(NullPointerException e) {
			e.printStackTrace();
			System.out.println(USAGE_STRING);
			Runtime.getRuntime().exit(NULL_POINTER_EXCEPTION);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(USAGE_STRING);
		}
		OutputStream outputStream = new FileOutputStream(output);
		json2Xml(new File(avroPath), xmlPath);
		xml2Beanio(outputStream, xmlPath, TRANSLET_PATH_STRING, userArguments);
	}
	
	@SuppressWarnings("unused")
	private static void getAndPut(Map<String, Object> parameters, String key, List<String> values, int index) {
		if(values.size() >= index + 1) {
			parameters.put(key, values.get(index + 1));
		}
	}
	
	public static void json2Xml2Beanio(File avroFile, String xmlPath, OutputStream outputStream, String transletPath, Map<String, Object> userArguments) {
		json2Xml(avroFile, xmlPath);
		xml2Beanio(outputStream, xmlPath, transletPath, userArguments);
	}
	
	private static void json2Xml(File avroFile, String xmlPath) {
		
	}
	
	private static void xml2Beanio(OutputStream outputStream, String xmlPath, String transletPath, Map<String, Object> userArguments) {
		
	}
}
