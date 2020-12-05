/**
 * 
 */
package com.github.cobolio.internal.compiler;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.LinkedHashSet;
import java.util.Properties;

import javax.xml.bind.JAXB;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.junit.Test;

import com.github.cobolio.internal.cobol.datatype.ExternalDecimal;
import com.github.cobolio.internal.cobol.datatype.PackedDecimal;
import com.github.cobolio.internal.cobol.datatype.UnicodeDecimal;
import com.github.cobolio.internal.compiler.CobolIOCompiler.FieldVisibility;
import com.github.cobolio.internal.compiler.CobolIOCompiler.InternalContext;
import com.github.cobolio.internal.xml.cb2xml.Copybook;
import com.github.cobolio.internal.xml.cb2xml.EnhancedCopybook;

import net.sf.cb2xml.Cb2Xml;

/**
 * @author Andrew
 *
 */
public class CobolIOCompilerTest {

	@Test
	public void testCobolIOCompiler() throws IOException {
		String inputTemplate = "./src/test/java/test.vm";
		String packge = "./src/test/resources/";
		String javaClassName = "TestObject";
		String xmlFileNamePath = packge + "CobolExample.xml";
		String outputJavaFilePath = packge + javaClassName + ".java";

		File cobolFile = new File("./src/test/resources/CobolExample.cbl");
		File xmlFile = new File(xmlFileNamePath);
		xmlFile.createNewFile();
		File javaFile = new File(outputJavaFilePath);
		javaFile.createNewFile();

		InputStream st = new FileInputStream(cobolFile);
		String xml = Cb2Xml.convertToXMLString(st, "MASTER_REC", true);
		Writer xmlWriter = new FileWriter(xmlFile);
		xmlWriter.write(xml);
		xmlWriter.close();
		Copybook cpy = JAXB.unmarshal(xmlFile, Copybook.class);
		EnhancedCopybook ecpy = new EnhancedCopybook(cpy);
		InternalContext cache = new InternalContext(javaClassName);
		cache.putCopybook(ecpy);
		LinkedHashSet<Class<?>> imports = new LinkedHashSet<>();
		imports.add(PackedDecimal.class);
		imports.add(ExternalDecimal.class);
		imports.add(UnicodeDecimal.class);
		cache.putImports(imports);
		cache.setFieldVisibility(FieldVisibility.PRIVATE);
		cache.setGettersReturnOptional(true);
		VelocityContext context = new VelocityContext(cache);
		

		Properties properties = new Properties();
		properties.put("input.encoding", "UTF-8");

		Writer writer = new FileWriter(javaFile);
		Reader reader = new FileReader("./src/main/java/com/github/cobolio/internal/compiler/cobolio.vm");
		Velocity.init(properties);
		if (Velocity.evaluate(context, writer, "cobolio.velocity.error.log", reader)) {
			System.out.println("Built successfully.");
		} else {
			System.err.println("Build failed.");
			fail();
		}
		writer.close();
		//Add logic for testing constructed class...
	}
}
