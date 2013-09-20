package de.gravitex.rp.core;

import java.io.File;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import de.gravitex.rp.logic.RPRemote;
import de.gravitex.rp.logic.WindowStateInfo;

public class RPConnector {
	
	private static final String PROP_WINDOWS_IDENTIFIER = "windowsIdentifier";

	private static final String PROP_NEW_WINDOW_STATE = "newWindowState";
	
	//WINDOWS
	private static final String BASE_DIR = "C:\\Users\\stefan.schulz\\Desktop\\raspberry\\messages";
	private static final String TARGET_DIR = "C:\\Users\\stefan.schulz\\Desktop\\raspberry\\messages\\processed\\";
	
	//LINUX
//	private static final String BASE_DIR = "/var/tmp/messages/";
//	private static final String TARGET_DIR = "/var/tmp/messages/processed/";
	
	private static final String ATTRIBUTE_IDENTIFIER = "identifier";

	private static final String ATTRIBUTE_STATE = "state";	

	private static final String EJB_MODULE_NAME = "rp-server";

	public static void main(String[] args) throws Exception {
		
		System.out.println("processing messages...");
		
//		Properties properties = parseCommandLine(args);
		
//		if (properties == null) {			
//			return;
//		}
		
		RPRemote testRemote = lookup(EJB_MODULE_NAME, "RPBean", RPRemote.class);
		
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		String attrIdentifier = null;
		String attrState = null;
		Document doc = null;
		Element root = null;
		File destination = null;
		for (File file : new File(BASE_DIR).listFiles()) {
			if (!(file.isDirectory())) {
				System.out.println(file.getName());	
				doc = dBuilder.parse(file);
				root = doc.getDocumentElement();				
				attrIdentifier = root.getAttribute(ATTRIBUTE_IDENTIFIER);
				attrState = root.getAttribute(ATTRIBUTE_STATE);
				testRemote.toggleWindowState(attrIdentifier, WindowStateInfo.valueOf(attrState));
				System.out.println("processing message [identifier:"+attrIdentifier+"|state:"+attrState+"]");				
				destination = new File(TARGET_DIR+file.getName());
				file.renameTo(destination);
			}			
		}
	}
	
	private static Properties parseCommandLine(String[] args) {
		BasicParser parser = new BasicParser();
		CommandLine commandLine = null;
		Options options = new Options();
		options.addOption(PROP_WINDOWS_IDENTIFIER, true, null);
		options.addOption(PROP_NEW_WINDOW_STATE, true, null);
		try {			
			commandLine = parser.parse(options, args);
		} catch (ParseException e) {
			System.out.println("could not parse command line ---> returning.");
			return null;
		}
		if (commandLine.getOptionValue(PROP_WINDOWS_IDENTIFIER) == null) {
			System.out.println("unsufficient arguments, windows identifier (-" + PROP_WINDOWS_IDENTIFIER + ") required ---> returning.");
			return null;
		}
		if (commandLine.getOptionValue(PROP_NEW_WINDOW_STATE) == null) {
			System.out.println("unsufficient arguments, new window state (-" + PROP_NEW_WINDOW_STATE + ") required ---> returning.");
			return null;
		}
		Properties properties = new Properties();
		properties.put(PROP_WINDOWS_IDENTIFIER, commandLine.getOptionValue(PROP_WINDOWS_IDENTIFIER));
		properties.put(PROP_NEW_WINDOW_STATE, commandLine.getOptionValue(PROP_NEW_WINDOW_STATE));
		return properties;
	}

	//@SuppressWarnings("unchecked")
	private static <T> T lookup(String moduleName, String beanName, Class<?> remoteClass) throws NamingException {
		final Context context = getInitialContext();
		String lookUp = "ejb:/"+moduleName+"/"+beanName+"!" + remoteClass.getName();
		System.out.println("looking up : "+lookUp+".");
		return (T) context.lookup(lookUp);
	}

	private static Context getInitialContext() throws NamingException {
		final Properties jndiProperties = new Properties();
		jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
		final Context context = new InitialContext(jndiProperties);
		return context;
	}
}
