/**
 * 
 */
package com.github.cobolio.internal.compiler;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Andrew
 *
 */
public class CobolData {
	/**
	 * List of Java reserved words from
	 * https://docs.oracle.com/javase/specs/jls/se8/html/jls-3.html#jls-3.9 combined
	 * with the boolean and null literals.
	 */
	protected static final Set<String> RESERVED_WORDS = new HashSet<>(Arrays.asList("abstract", "assert", "boolean",
			"break", "byte", "case", "catch", "char", "class", "const", "continue", "default", "do", "double", "else",
			"enum", "extends", "false", "final", "finally", "float", "for", "goto", "if", "implements", "import",
			"instanceof", "int", "interface", "long", "native", "new", "null", "package", "private", "protected",
			"public", "return", "short", "static", "strictfp", "super", "switch", "synchronized", "this", "throw",
			"throws", "transient", "true", "try", "void", "volatile", "while"));
}
