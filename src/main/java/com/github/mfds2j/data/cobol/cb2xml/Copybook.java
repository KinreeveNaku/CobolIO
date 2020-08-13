package com.github.mfds2j.data.cobol.cb2xml;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * This class reflects the cb2xml formatting of a copybook. This is to be kept
 * relatively identical to the actual {@link net.sf.cb2xml.jaxb.Copybook}, but
 * is used as its own class for potential changes required in future builds.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "items" })
@XmlRootElement(name = "copybook")
class Copybook {
	@XmlElement(required = true)
	protected List<EnhancedItem> enhancedItems;
	@XmlAttribute(name = "filename", required = true)
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	@XmlSchemaType(name = "token")
	protected String filename;

	/**
	 * Contains a list of this copybook's first level items.
	 * 
	 * @return Returns a list of all this copybook's first level items.
	 */
	public List<EnhancedItem> getItems() {
		if (this.enhancedItems == null) {
			this.enhancedItems = new ArrayList<>();
		}

		return this.enhancedItems;
	}

	public String getFilename() {
		return this.filename;
	}

	public void setFilename(String value) {
		this.filename = value;
	}
}
