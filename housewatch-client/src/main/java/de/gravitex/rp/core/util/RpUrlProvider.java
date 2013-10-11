package de.gravitex.rp.core.util;

public class RpUrlProvider {

	public static String gimmeLogDirectory(SystemType systemType) {
		switch (systemType) {
		case LINUX:
			// ------------------------------------------
			return "/opt/fhem/log/";
			// ------------------------------------------
		case WIN_7:
			// ------------------------------------------
			return "C:\\fhemlog\\";
			// ------------------------------------------
		default:
			// ------------------------------------------
			return null;
			// ------------------------------------------
		}
	}

	public static String gimmeMessagesDirectory(SystemType systemType) {
		switch (systemType) {
		case LINUX:
			// ------------------------------------------
			return "/var/tmp/messages/";
			// ------------------------------------------
		case WIN_7:
			// ------------------------------------------
			return "C:\\Users\\Schulz\\Dropbox\\raspberry\\messages\\";
			// ------------------------------------------
		default:
			// ------------------------------------------
			return null;
			// ------------------------------------------
		}
	}
}
