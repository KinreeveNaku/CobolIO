package com.github.cobolio.internal.xml.cb2xml;

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

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonRootName;

/**
 * This class reflects the cb2xml formatting of a copybook. This is to be kept
 * relatively identical to the actual {@link net.sf.cb2xml.jaxb.Copybook}, but
 * is used as its own class for potential changes required in future builds.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "item" })
@XmlRootElement(name = "copybook")
@JsonRootName(value = "copybook")
public class Copybook {
	@XmlElement(name = "item", required = true)
	@JsonAlias("item")
	protected List<EnhancedItem> item;
	@XmlAttribute(name = "filename", required = true)
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	@XmlSchemaType(name = "token")
	@JsonAlias("filename")
	protected String filename;
	@XmlAttribute(name = "dialect", required = false)
	protected String dialect;
	public Copybook() {
		
	}
	
	public Copybook(List<Object> items) {
		
	}

	/**
	 * Contains a list of this copybook's first level items.
	 * 
	 * @return Returns a list of all this copybook's first level items.
	 */
	public List<EnhancedItem> getItems() {
		if (this.item == null) {
			this.item = new ArrayList<>();
		}
		return this.item;
	}
	
	public void setItems(List<EnhancedItem> item) {
		this.item = item;
	}
	
	public String getFilename() {
		return this.filename;
	}

	public void setFilename(String value) {
		this.filename = value;
	}
	
	public String getDialect() {
		return this.dialect;
	}
	
	public void setDialect(String dialect) {
		this.dialect = dialect;
	}
	
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof Copybook)) {
			return false;
		}
		return this.hashCode() == o.hashCode();
	}
	
	@Override
	public int hashCode() {
		return java.util.Objects.hash(filename, item);
	}
}
