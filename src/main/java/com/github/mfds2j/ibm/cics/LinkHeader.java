/**
 * 
 */
package com.github.mfds2j.ibm.cics;

/**
 * A class used for external interaction with the application. External classes
 * can use this information to make API calls to the underlying system for their
 * own event handling in situations such as failures or relogging, etc.
 * 
 * @author Andrew
 *
 */
public class LinkHeader {
	private String eventName;
	private String progName;
	private String eventType;
	private String sysId;
	private String versionId;

	public LinkHeader(String[] header) {

	}

	public String getEventName() {
		return this.eventName;
	}

	public String getProgName() {
		return progName;
	}

	public String getEventType() {
		return eventType;
	}

	public String getSysId() {
		return sysId;
	}

	public String getVersionId() {
		return versionId;
	}
}
