package de.gravitex.rp.core;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import de.gravitex.rp.clientutil.RPClientUtil;
import de.gravitex.rp.logic.RPRemote;
import de.gravitex.rp.logic.WindowStateInfo;

public class RPConnector {	
	
	//WINDOWS
//	private static final String BASE_DIR = "C:\\Users\\Schulz\\Dropbox\\raspberry\\messages";
//	private static final String TARGET_DIR = "C:\\Users\\Schulz\\Dropbox\\raspberry\\messages\\processed\\";
	
	//LINUX
	private static final String BASE_DIR = "/var/tmp/messages/";
	private static final String TARGET_DIR = "/var/tmp/messages/processed/";
	
	private static final String ATTRIBUTE_IDENTIFIER = "identifier";

	private static final String ATTRIBUTE_STATE = "state";	

	private static final String EJB_MODULE_NAME = "rp-server";

	public static void main(String[] args) throws Exception {
		
		System.out.println("processing messages...");		
		
		RPRemote testRemote = RPClientUtil.lookup(EJB_MODULE_NAME, "RPBean", RPRemote.class);
		
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
}
