/**
 * 
 */
package com.github.mfds2j.data.cobol.cb2xml;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

import com.github.mfds2j.Alpha;

/**
 * EnhancedCopybook is a wrapper class for {@link net.sf.cb2xml.jaxb.Copybook
 * Copybook} that has additional functionality for improving performance of
 * particular behaviors employed by this library such as storing hierarchical
 * paths of fields for faster access of same level fields and more versatility
 * for future design implementations.
 * 
 * @author Andrew
 *
 */
@Alpha(major = 0, minor = 0, snapshot = 5)
public class EnhancedCopybook {
	private static final int DEFAULT_MAP_SIZE = 512;
	private static final String PERIOD = ".";
	private final Copybook cpy;
	private final int length;
	private final LinkedHashMap<String, EnhancedItem> classVariables;// Relying on Items referenced by their path should
																// noticeably improve the performance of behaviors such
																// as field concatenation, redefines, occurs, occurs
																// depending, etc. by being able to look up fields or
																// their parents directly rather than recursively.

	public EnhancedCopybook(Copybook cpy) {
		if (cpy == null || cpy.getItems().isEmpty()) {// Defensive
			throw new NullPointerException(
					"The copybook cannot be null and must have some kind of internal structure.");
		}
		this.cpy = cpy;
		this.classVariables = new LinkedHashMap<>(DEFAULT_MAP_SIZE);// We won't know the field count until after and
																	// it's more efficient to just assume some
																	// relatively high number instead of parsing the
																	// copybook twice. Especially with larger copybooks.
		this.length = this.primePaths();
		
	}

	public Copybook getCopybook() {
		return this.cpy;
	}
	
	public synchronized String getName() {
		return this.cpy.getFilename();
	}
	
	/**
	 * Returns an unmodifiable list of the fields in this copybook.
	 * @return
	 */
	public synchronized List<EnhancedItem> getItems() {
		return Collections.unmodifiableList(this.cpy.getItems());
	}

	/**
	 * Generates a Map containing key-value pairs of paths and the items which they
	 * point to. This method simultaneously initializes the internal paths of each
	 * item and stores it within both the item itself and the map. This
	 * functionality is heavily used internally for behaviors used for resolving
	 * redefines and occurs depending instances as well as field concatenations for
	 * various purposes.
	 * 
	 * @param cpy
	 * @return Returns a Map containing key-value pairs of copybook field paths keys
	 *         and item characteristics values.
	 */
	private int primePaths() {
		if (cpy == null) {
			throw new IllegalStateException("The copybook cannot be null");
		}
		int len = 0;
		for (EnhancedItem enhancedItem : cpy.getItems()) {
			enhancedItem.setFieldPath(makePath(cpy.getFilename(), enhancedItem.getName()));
			if (enhancedItem.getItems() != null && !enhancedItem.getItems().isEmpty()) {
				for(EnhancedItem x : enhancedItem.getItems()) {
					len += primePaths(x, cpy.getFilename(), len);//add the total length of each Item to the total length.
					this.classVariables.put(x.getFieldPath(), x);
				}
			}
		}
		return len;
	}

	private int primePaths(EnhancedItem enhancedItem, String parentPath, int len) {
		formPath(enhancedItem, parentPath);
		if (enhancedItem.getItems() != null && !enhancedItem.getItems().isEmpty()) {
			for(EnhancedItem x : enhancedItem.getItems()) {
				formPath(enhancedItem, parentPath);
				this.classVariables.put(x.getFieldPath(), x);
			}
		} else {
			len += enhancedItem.getDisplayLength();//add the length of this item to the length of the current context.
		}
		return len;
	}

	private static void formPath(EnhancedItem enhancedItem, String parentPath) {
		enhancedItem.setFieldPath(makePath(parentPath, enhancedItem.getName()));
	}

	/**
	 * @param parentPath
	 * @param name
	 * @return
	 */
	private static String makePath(String parentPath, String name) {
		return parentPath.concat(PERIOD.concat(name));
	}

	public int getLength() {
		return length;
	}
	
	

}
