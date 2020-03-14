/**
 * 
 */
package com.github.mfds2j;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;


/**
 * @author Andrew
 *
 */
public class MFDSApplication {
	private static final Logger ROOT = (Logger) LoggerFactory.getLogger(MFDSApplication.class);
	public static void main(String[] args) {

		System.out.println(Messages.getString("MFDSApplication.LINE0")); //$NON-NLS-1$
		System.out.println(Messages.getString("MFDSApplication.LINE1")); //$NON-NLS-1$
		System.out.println(Messages.getString("MFDSApplication.LINE2")); //$NON-NLS-1$
		System.out.println(Messages.getString("MFDSApplication.LINE3")); //$NON-NLS-1$
		System.out.println(Messages.getString("MFDSApplication.LINE4")); //$NON-NLS-1$
		System.out.println(Messages.getString("MFDSApplication.LINE5")); //$NON-NLS-1$
		System.out.println(Messages.getString("MFDSApplication.LINE6")); //$NON-NLS-1$
		ROOT.info("Initializing MFDS2J...");
	}
}
