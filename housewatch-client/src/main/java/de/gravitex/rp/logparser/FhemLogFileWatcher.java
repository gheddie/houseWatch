package de.gravitex.rp.logparser;

import java.util.Properties;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import de.gravitex.rp.core.exception.FhemException;
import de.gravitex.rp.core.util.SystemType;

public class FhemLogFileWatcher {
	
	private static final String PROP_COMPONENT_NAME = "componentName";
	
	private static final String PROP_OS_TYPE = "osType";

	public static void main(String[] args) {
		try {
			Properties properties = parseCommandLine(args);
			if (properties == null) {			
				return;
			}
			new FhemLogParser(properties.getProperty(PROP_COMPONENT_NAME), (SystemType) properties.get(PROP_OS_TYPE)).writeMessage();	
		} catch (FhemException e) {
			e.printStackTrace();
		}
	}
	
	private static Properties parseCommandLine(String[] args) throws FhemException {
		BasicParser parser = new BasicParser();
		CommandLine commandLine = null;
		Options options = new Options();
		options.addOption(PROP_COMPONENT_NAME, true, null);
		options.addOption(PROP_OS_TYPE, true, null);
		try {			
			commandLine = parser.parse(options, args);
		} catch (ParseException e) {
			System.out.println("could not parse command line ---> returning.");
			return null;
		}
		if (commandLine.getOptionValue(PROP_COMPONENT_NAME) == null) {
			System.out.println("unsufficient arguments, component name (-" + PROP_COMPONENT_NAME + ") required ---> returning.");
			return null;
		}
		String optionValueOsType = commandLine.getOptionValue(PROP_OS_TYPE);
		if (optionValueOsType == null) {
			System.out.println("unsufficient arguments, os type (-" + PROP_OS_TYPE + ") required ---> returning.");
			return null;
		}		
		Properties properties = new Properties();
		properties.put(PROP_COMPONENT_NAME, commandLine.getOptionValue(PROP_COMPONENT_NAME));
		SystemType systemType = SystemType.valueOf(optionValueOsType);
		if (systemType == null) {
			throw new FhemException("unable to determine system type for string '"+optionValueOsType+"'.");
		}
		properties.put(PROP_OS_TYPE, systemType);
		return properties;
	}
}
