/**
 * 
 */
package net.sf.zrecord;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.FileSystemException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import net.sf.cb2xml.sablecc.lexer.LexerException;
import net.sf.cb2xml.sablecc.parser.ParserException;
import net.sf.cobol2j.Cobol2JException;
import net.sf.cobol2j.translets.Cb2xc2j;

/**
 * @author Andrew
 *
 */
public class Cobol2JWrapper {
	private Cobol2JWrapper() {
		throw new IllegalArgumentException();
	}

	public static final void cb2Xml2Cobol2J(String filepath, String xmlOutPath) throws Cobol2JException {
		try {
			File temp = new File(filepath);
			if (!temp.exists()) {
				throw new FileNotFoundException("The input filepath provided does not exist");
			}
			File out = new File(xmlOutPath);
			if (!out.exists()) {
				if (!out.createNewFile()) {
					// The destination filepath does not exist and could not be created.
					throw new FileSystemException(xmlOutPath);
				}
			}
			OutputStream stream = new FileOutputStream(out);
			Cb2xc2j.cb2xc2j(stream, filepath);
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
}
