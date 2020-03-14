package net.sf.cobol2j.translets;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PushbackReader;
import java.io.StringReader;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;

import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Document;

import com.github.mfds2j.Alpha;

import net.sf.cb2xml.CobolPreprocessor;
import net.sf.cb2xml.CopyBookAnalyzer;
import net.sf.cb2xml.sablecc.lexer.Lexer;
import net.sf.cb2xml.sablecc.lexer.LexerException;
import net.sf.cb2xml.sablecc.node.Start;
import net.sf.cb2xml.sablecc.parser.Parser;
import net.sf.cb2xml.sablecc.parser.ParserException;
import net.sf.cobol2j.Cobol2JException;

@SuppressWarnings({ "deprecation", "squid:CallToDeprecatedMethod" })
public class Cb2xc2j {
	private static final String TRANSLET_NAME = "cb2xml2cobol2j";

	public static void main(String[] args) {
		try {
			Cb2xc2j.cb2xc2j(System.out, args[0]);
		} catch (ParserException e) {
			throw new Cobol2JException("A ParserException was thrown while preprocessing the file.", e);
		} catch (LexerException e) {
			throw new Cobol2JException("A LexerException was thrown while processing the file.", e);
		} catch (IOException e) {
			throw new Cobol2JException("An IOException was thrown while reading from the file.", e);
		} catch (TransformerException e) {
			throw new Cobol2JException("A TransformerException was thrown while performing an XSLT transformation.", e);
		} catch (ParserConfigurationException e) {
			throw new Cobol2JException("An invalid configuration was provided to the xmlparser instance.", e);
		}
	}

	/**
	 * This method is a compatibility resolution for {@link com.ibm.jzos.ZFile}
	 * which makes use of {@ link java.io.InputStream} for its file reading
	 * interface. Use of this method makes the assumption that the input stream is
	 * all plaintext and not encoded in multiple encodings.
	 * 
	 * @param os
	 * @param fis
	 * @throws IOException
	 * @throws LexerException
	 * @throws ParserException
	 * @throws TransformerException
	 * @throws ParserConfigurationException
	 */
	@Alpha(major = 0, minor = 0, snapshot = 9)
	public static final void cb2xc2j(OutputStream os, InputStream fis, String cpyName, int columnStart, int columnEnd)
			throws ParserException, LexerException, IOException, TransformerException, ParserConfigurationException {
		String preprocessed = CobolPreprocessor.preProcess(fis, columnStart, columnEnd);
		StringReader sr = new StringReader(preprocessed);
		PushbackReader pbr = new PushbackReader(sr, 1000);
		Lexer lexer = new Lexer(pbr);
		Parser parser = new Parser(lexer);
		Start ast = parser.parse(columnStart);
		CopyBookAnalyzer cpyAnalyzer = new CopyBookAnalyzer(cpyName, parser);
		ast.apply(cpyAnalyzer);
		Document cb2xmlDoc = cpyAnalyzer.getDocument();
		System.setProperty("javax.xml.transform.TransformerFactory",
				"org.apache.xalan.xsltc.trax.TransformerFactoryImpl");
		TransformerFactory xformFactory = TransformerFactory.newInstance();
		xformFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
		xformFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, "");
		xformFactory.setAttribute("use-classpath", Boolean.TRUE);
		xformFactory.setAttribute("package-name", "net.sf.cobol2j.translets");
		Transformer transformer = xformFactory.newTransformer(new StreamSource(TRANSLET_NAME));
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, Boolean.TRUE);
		DocumentBuilder docbuilder = factory.newDocumentBuilder();
		Document xc2jdoc = docbuilder.newDocument();
		DOMResult res = new DOMResult(xc2jdoc);
		transformer.transform(new DOMSource(cb2xmlDoc), res);
		OutputFormat format = new OutputFormat(xc2jdoc);
		format.setLineWidth(120);
		format.setIndenting(true);
		format.setIndent(2);
		XMLSerializer serializer = new XMLSerializer(os, format);
		serializer.serialize(xc2jdoc);
	}

	/**
	 * This method reads a copybook from as file and translates it into xml
	 * understandable and usable to cobol2j. The provided file path must point to a
	 * file that is in pure plaintext and using the native encoding.
	 * 
	 * @param os
	 * @param filepath
	 * @throws ParserException
	 * @throws LexerException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 * @throws TransformerException
	 */
	public static final void cb2xc2j(OutputStream os, String filepath)
			throws ParserException, LexerException, IOException, ParserConfigurationException, TransformerException {

		// Document cb2xmldoc = null;
		// Lexer lexer = null;
		File file = new File(filepath);
		cb2xc2j(os, new FileInputStream(filepath), file.getName(), 6, 72);
		/*
		 * String preProcessed = CobolPreprocessor.preProcess(file); StringReader sr =
		 * new StringReader(preProcessed); PushbackReader pbr = new PushbackReader(sr,
		 * 1000); lexer = new Lexer(pbr); Parser parser = new Parser(lexer); Start ast =
		 * parser.parse(6);// int param is the initial column CopyBookAnalyzer
		 * copyBookAnalyzer = new CopyBookAnalyzer(file.getName(), parser);
		 * ast.apply(copyBookAnalyzer); cb2xmldoc = copyBookAnalyzer.getDocument();
		 * System.setProperty("javax.xml.transform.TransformerFactory",
		 * "org.apache.xalan.xsltc.trax.TransformerFactoryImpl"); TransformerFactory
		 * xformFactory = TransformerFactory.newInstance();
		 * xformFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
		 * xformFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, "");
		 * xformFactory.setAttribute("use-classpath", Boolean.TRUE);
		 * xformFactory.setAttribute("package-name", "net.sf.cobol2j.translets");
		 * Transformer transformer = xformFactory.newTransformer(new
		 * StreamSource(TRANSLET_NAME)); DocumentBuilderFactory factory =
		 * DocumentBuilderFactory.newInstance();
		 * factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, Boolean.TRUE);
		 * DocumentBuilder docbuilder = factory.newDocumentBuilder(); Document xc2jdoc =
		 * docbuilder.newDocument(); DOMResult res = new DOMResult(xc2jdoc);
		 * transformer.transform(new DOMSource(cb2xmldoc), res); OutputFormat format =
		 * new OutputFormat(xc2jdoc); format.setLineWidth(120);
		 * format.setIndenting(true); format.setIndent(2); XMLSerializer serializer =
		 * new XMLSerializer(os, format); serializer.serialize(xc2jdoc);
		 */
	}
}