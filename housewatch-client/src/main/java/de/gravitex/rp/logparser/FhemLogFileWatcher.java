package de.gravitex.rp.logparser;

import java.util.Properties;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class FhemLogFileWatcher {
	
	private static final String PROP_COMPONENT_NAME = "componentName";

	public static void main(String[] args) {
		Properties properties = parseCommandLine(args);
		if (properties == null) {			
			return;
		}
//		new FhemLogParser("CUL_HM_HM_SEC_SC_219D3D").writeMessage();
		new FhemLogParser(properties.getProperty(PROP_COMPONENT_NAME)).writeMessage();
	}
	
	private static Properties parseCommandLine(String[] args) {
		BasicParser parser = new BasicParser();
		CommandLine commandLine = null;
		Options options = new Options();
		options.addOption(PROP_COMPONENT_NAME, true, null);
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
		Properties properties = new Properties();
		properties.put(PROP_COMPONENT_NAME, commandLine.getOptionValue(PROP_COMPONENT_NAME));
		return properties;
	}
}
