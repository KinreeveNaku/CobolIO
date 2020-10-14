/**
 * 
 */
package com.github.cobolio.internal.xml;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.bind.JAXB;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.velocity.VelocityContext;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.github.Version;
import com.github.cobolio.internal.util.CobolIOException;
import com.github.cobolio.internal.xml.cb2xml.Copybook;
import com.github.cobolio.internal.xml.cb2xml.EnhancedCopybook;

import net.sf.cb2xml.Cb2Xml;

/**
 * @author Andrew
 *
 */
@Version(major = 0, minor = 1, bugfix = 0)
public final class Cb2Xml2Velocity {
	private static final Class<Copybook> COPY_CLASS = Copybook.class;
	private static final DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	private static final TransformerFactory tFactory = TransformerFactory.newInstance();

	private Cb2Xml2Velocity() {

	}

	public static VelocityContext xml2Velocity(Copybook cpy, VelocityContext context) {
		EnhancedCopybook enhancedCpy = new EnhancedCopybook(cpy);
		context.put("copybookItems", enhancedCpy.getItems());
		// TODO continue logic
		return context;
	}

	public static Copybook cb2Xml(File cobolFile) {
		return JAXB.unmarshal(Cb2Xml.convertToXMLString(cobolFile), COPY_CLASS);
	}

	public static Copybook unmarshal(File xmlFile) {
		return JAXB.unmarshal(xmlFile, COPY_CLASS);
	}

	public static Copybook unmarshal(String xmlString) {
		return unmarshal(new ByteArrayInputStream(xmlString.getBytes(UTF_8)));
	}

	public static Copybook unmarshal(InputStream xmlInputStream) {
		return JAXB.unmarshal(xmlInputStream, COPY_CLASS);
	}

	public static void marshal(Copybook cpy, File xmlFile) {
		JAXB.marshal(cpy, xmlFile);
	}

	public static void marshal(Copybook cpy, OutputStream xmlOutputStream) {
		JAXB.marshal(cpy, xmlOutputStream);
	}

	/**
	 * Writes a Copybook to XML and store it to the specified location.
	 * 
	 * @param cpy The Copybook to be marshalled into XML.
	 * @param xml The string is first interpreted as an absolute URI.If it's not a
	 *            valid absolute URI,then it's interpreted as a File
	 */
	public static void marshal(Copybook cpy, String xml) {
		JAXB.marshal(cpy, xml);
	}

	public static Result xslTransform(File xmlFile, File xsltFile) throws CobolIOException {
		try {
			DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = dbFactory.newDocumentBuilder();
			Document document = builder.parse(xmlFile);
			StreamSource stylesource = new StreamSource(xsltFile);
			Transformer transformer = tFactory.newTransformer(stylesource);
			DOMSource source = new DOMSource(document);
			StreamResult result = new StreamResult(xmlFile);
			transformer.transform(source, result);
			return result;
		} catch (TransformerConfigurationException e) {
			throw new CobolIOException("\n** Transformer Factory error\r\n   " + e.getException().getMessage(), e);
		} catch (TransformerException e) {
			throw new CobolIOException("\n** Transformation error\r\n   " + e.getException().getMessage(), e);
		} catch (SAXException e) {
			// Error generated by this application
			// (or a parser-initialization error)
			throw new CobolIOException("\n** SAX error\r\n   " + e.getException().getMessage(), e);
		} catch (ParserConfigurationException e) {
			// Parser with specified options can't be built
			throw new CobolIOException("com.github.cobolio.internal.xml.Cb2Xml2Velocity.XmlPCE", e, true);
		} catch (IOException e) {
			throw new CobolIOException("com.github.cobolio.internal.xml.Cb2Xml2Velocity.transformationFailedAtIO", e, true);
		}
	}
	
	public static Result xslTransform(Document xml, Document xsl) throws CobolIOException {
		try {
			
			DOMSource xmlSource = new DOMSource(xml);
			DOMSource styleSource = new DOMSource(xsl);
			Transformer transformer = tFactory.newTransformer(styleSource);
			Document dest = dbFactory.newDocumentBuilder().newDocument();
			DOMResult result = new DOMResult(dest);
			transformer.transform(xmlSource, result);
			return result;
		} catch (TransformerConfigurationException e) {
			throw new CobolIOException("\n** Transformer Factory error\r\n   " + e.getException().getMessage(), e);
		} catch (TransformerException e) {
			throw new CobolIOException("\n** Transformation error\r\n   " + e.getException().getMessage(), e);
		} catch (ParserConfigurationException e) {
			// Parser with specified options can't be built
			throw new CobolIOException("com.github.cobolio.internal.xml.Cb2Xml2Velocity.XmlPCE", e, true);
		}
		
	}
}
