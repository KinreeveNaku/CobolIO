/**
 * 
 */
package com.github.mfds2j.ibm.cics;

/**
 * @author Andrew
 *
 */
public class CommAreaHolder {
	private LinkHeader header;
	public CommAreaHolder(String[] args) {
		
	}
	public LinkHeader getHeader() {
		return header;
	}
	public void setHeader(final LinkHeader header) {
		this.header = header;
	}
}
