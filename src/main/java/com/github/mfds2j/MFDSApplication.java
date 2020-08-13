/**
 * 
 */
package com.github.mfds2j;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.LoggerFactory;

import com.github.mfds2j.ibm.cics.CommAreaHolder;

import ch.qos.logback.classic.Logger;


/**
 * @author Andrew
 *
 */
@Resource(name = "ApplicationLauncher")
public class MFDSApplication {
	@SuppressWarnings({"java:S1312"})
	private static final Logger ROOT_LOGGER = (Logger) LoggerFactory.getLogger(MFDSApplication.class);
	public static void main(String[] args) {
		title();
		List<String> arguments = Arrays.asList(args);
		//TODO Add initializing logic
		
	}
	
	@SuppressWarnings({"S1172"})
	public static void main(CommAreaHolder comm) {
		title();
		//TODO Add JCICS integration support
	}
	
	@SuppressWarnings({"java:S106"})
	private static void title() {
		System.out.println(Messages.getString("MFDSApplication.LINE0")); //$NON-NLS-1$
		System.out.println(Messages.getString("MFDSApplication.LINE1")); //$NON-NLS-1$
		System.out.println(Messages.getString("MFDSApplication.LINE2")); //$NON-NLS-1$
		System.out.println(Messages.getString("MFDSApplication.LINE3")); //$NON-NLS-1$
		System.out.println(Messages.getString("MFDSApplication.LINE4")); //$NON-NLS-1$
		System.out.println(Messages.getString("MFDSApplication.LINE5")); //$NON-NLS-1$
		System.out.println(Messages.getString("MFDSApplication.LINE6")); //$NON-NLS-1$
		ROOT_LOGGER.info("Initializing MFDS2J...");
	}
}
